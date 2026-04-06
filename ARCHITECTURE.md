# Smart Parking System - Architecture Diagram

## System Architecture Overview

```mermaid
graph TB
    subgraph "Client Layer"
        CLIENT["HTTP Client / API Consumer"]
    end

    subgraph "API Layer"
        EC["Entry Controller<br/>(POST /api/entry)<br/>(POST /api/exit)"]
    end

    subgraph "Service Layer"
        ES["Entry Service<br/>- Records vehicle entry<br/>- Publishes VehicleEnteredEvent"]
        
        EXS["Exit Service<br/>- Records vehicle exit<br/>- Publishes VehicleExitedEvent"]
    end

    subgraph "Event System - Pub/Sub"
        EP["Event Publisher<br/>(Spring ApplicationEventPublisher)"]
        
        VEE["VehicleEnteredEvent"]
        VXE["VehicleExitedEvent"]
    end

    subgraph "Event Listeners - Subscribers"
        BS["Billing Service<br/>@EventListener<br/>- Calculates parking charges<br/>- Saves billing records"]
        
        NS["Notification Service<br/>@EventListener<br/>- Sends entry notifications<br/>- Sends exit notifications"]
    end

    subgraph "Data Access Layer"
        PER["ParkingEntryRepository<br/>(JPA)"]
        BR["BillingRepository<br/>(JPA)"]
        SR["SlotRepository<br/>(JPA)"]
    end

    subgraph "Data Layer"
        PED["ParkingEntry Entity<br/>- vehicleNumber<br/>- entryTime<br/>- exitTime<br/>- active"]
        
        BRD["BillingRecord Entity<br/>- vehicleNumber<br/>- amount<br/>- billedTime"]
        
        SD["Slot Entity<br/>- slotCode<br/>- available<br/>- vehicleNumber"]
    end

    subgraph "Database"
        DB[("PostgreSQL<br/>smart-parking-system<br/>- parking_entry<br/>- billing_record<br/>- slot")]
    end

    CLIENT -->|HTTP POST| EC
    EC -->|vehicleNumber| ES
    EC -->|vehicleNumber| EXS
    
    ES -->|PublishEvent| EP
    EXS -->|PublishEvent| EP
    
    EP -->|VehicleEnteredEvent| VEE
    EP -->|VehicleExitedEvent| VXE
    
    VEE -->|@EventListener| NS
    VEE -->|@EventListener| BS
    VXE -->|@EventListener| NS
    VXE -->|@EventListener| BS
    
    ES -->|Save| PER
    EXS -->|Update| PER
    BS -->|Save| BR
    SR -.->|Allocate Slot| ES
    
    PER -->|Persist| DB
    BR -->|Persist| DB
    SR -->|Persist| DB
    
    DB -->|Query| PED
    DB -->|Query| BRD
    DB -->|Query| SD

    style CLIENT fill:#e1f5ff
    style EC fill:#b3e5fc
    style ES fill:#80deea
    style EXS fill:#80deea
    style EP fill:#4dd0e1
    style VEE fill:#26c6da
    style VXE fill:#26c6da
    style BS fill:#00bcd4
    style NS fill:#00bcd4
    style PER fill:#0097a7
    style BR fill:#0097a7
    style SR fill:#0097a7
    style DB fill:#006064
```

## Architecture Pattern: Modular Monolith with Event-Driven Pub/Sub

### Key Components:

1. **Entry Module**
   - Controllers: Handle HTTP requests for vehicle entry/exit
   - EntryService: Business logic for vehicle entry
   - ExitService: Business logic for vehicle exit

2. **Allocation Module**
   - Slot management and availability tracking
   - Parking slot assignment

3. **Billing Module**
   - Event-driven billing calculation
   - Charges based on parking duration
   - Pricing: ₹20 minimum, ₹50 per hour

4. **Notification Module**
   - Event-driven notifications
   - Notifies on vehicle entry and exit

5. **Event System (Pub/Sub)**
   - VehicleEnteredEvent: Published when vehicle enters
   - VehicleExitedEvent: Published when vehicle exits
   - Uses Spring's ApplicationEventPublisher for loose coupling

### Data Flow:

```
Vehicle Entry Flow:
1. Client calls POST /api/entry?vehicleNumber=ABC123
2. EntryController → EntryService
3. EntryService creates ParkingEntry record
4. EntryService publishes VehicleEnteredEvent
5. NotificationService listens and sends entry notification
6. Entry saved to database

Vehicle Exit Flow:
1. Client calls POST /api/exit?vehicleNumber=ABC123
2. ExitController → ExitService
3. ExitService retrieves ParkingEntry
4. ExitService sets exitTime and marks as inactive
5. ExitService publishes VehicleExitedEvent
6. BillingService listens and calculates charges
7. NotificationService listens and sends exit notification
8. Changes saved to database
```

### Benefits of This Architecture:

- **Modularity**: Concerns are separated into distinct modules
- **Loose Coupling**: Services communicate through events, not direct calls
- **Scalability**: Event listeners can be moved to separate services
- **Testability**: Each module can be tested independently
- **Maintainability**: Clear separation of concerns makes the code easier to maintain
- **Extensibility**: New features can be added as new event listeners
