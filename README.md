# 🅿️ Smart Parking System

A modern, event-driven **Modular Monolith** architecture demonstration built with **Spring Boot**, **Spring Modulith**, and **PostgreSQL**. This project showcases how to build scalable, maintainable applications using pub/sub event architecture.

> **Architecture Pattern**: Modular Monolith with Event-Driven Pub/Sub  
> **Framework**: Spring Boot 4.0.5 | **Language**: Java 17  
> **Build Tool**: Maven | **Database**: PostgreSQL  

---

## 📋 Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [How It Works](#how-it-works)
- [Event-Driven Flow](#event-driven-flow)
- [Modules](#modules)
- [Bug Fixes](#bug-fixes)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

✅ **Vehicle Entry Management** - Register vehicles entering the parking lot  
✅ **Vehicle Exit Management** - Track vehicles leaving and calculate charges  
✅ **Automatic Billing** - Real-time charge calculation based on duration  
✅ **Notifications** - Event-triggered notifications for entry/exit  
✅ **Parking Slot Management** - Track available parking slots  
✅ **RESTful API** - Simple HTTP endpoints for all operations  
✅ **Event-Driven Architecture** - Loose coupling using Spring Events (Pub/Sub)  
✅ **Modular Design** - Separated concerns with Spring Modulith  
✅ **PostgreSQL Integration** - Persistent data storage with JPA/Hibernate  

---

## 🏗️ Architecture Overview

This is a **Modular Monolith** with **Event-Driven Pub/Sub** pattern. All modules run in a single Spring Boot application but communicate through published events, enabling loose coupling and high cohesion.

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                             │
│                    (HTTP API Consumers)                         │
└────────────────────────┬────────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────────┐
│                   CONTROLLER LAYER                              │
│              EntryController (/api/entry, /api/exit)            │
└────────────┬──────────────────────────────────────┬─────────────┘
             │                                      │
    ┌────────▼────────┐                    ┌────────▼────────┐
    │ EntryService    │                    │ ExitService     │
    │ - Vehicle entry │                    │ - Vehicle exit  │
    │ - Publish event │                    │ - Publish event │
    └────────┬────────┘                    └────────┬────────┘
             │                                      │
             └──────────────┬───────────────────────┘
                           │
          ┌────────────────▼────────────────────┐
          │   EVENT SYSTEM (Pub/Sub)            │
          │   ApplicationEventPublisher         │
          │   - VehicleEnteredEvent             │
          │   - VehicleExitedEvent              │
          └────────────┬──────────────┬─────────┘
                       │              │
        ┌──────────────▼──┐   ┌───────▼──────────────┐
        │ Billing Service │   │ Notification Service│
        │ @EventListener  │   │ @EventListener       │
        │ - Calculate fee │   │ - Send notifications │
        └──────────────┬──┘   └───────┬──────────────┘
                       │              │
          ┌────────────▼──────────────▼──────┐
          │   DATA ACCESS LAYER (JPA)        │
          │   Repositories                   │
          │   - ParkingEntryRepository       │
          │   - BillingRepository            │
          │   - SlotRepository               │
          └────────────┬──────────────┬──────┘
                       │              │
          ┌────────────▼──────────────▼──────┐
          │   DATABASE (PostgreSQL)          │
          │   - parking_entry table          │
          │   - billing_record table         │
          │   - slot table                   │
          └─────────────────────────────────┘
```

### Key Benefits

| Feature | Benefit |
|---------|---------|
| **Modular Design** | Easy to understand and modify individual modules |
| **Event-Driven** | Services are loosely coupled, communicate via events |
| **Scalable** | Can extract modules into microservices later |
| **Testable** | Each module can be tested independently |
| **Maintainable** | Clear separation of concerns |

---

## 📁 Project Structure

```
smart-parking-system/
├── src/
│   ├── main/
│   │   ├── java/org/devops/smartparkingsystem/
│   │   │   ├── SmartParkingSystemApplication.java      # Main Spring App
│   │   │   │
│   │   │   ├── entry/                                  # Entry Module
│   │   │   │   ├── EntryController.java                # REST Endpoints
│   │   │   │   ├── EntryService.java                   # Entry Logic
│   │   │   │   ├── ExitService.java                    # Exit Logic
│   │   │   │   ├── ParkingEntry.java                   # Entity Model
│   │   │   │   └── ParkingEntryRepository.java         # Data Access
│   │   │   │
│   │   │   ├── allocation/                             # Allocation Module
│   │   │   │   ├── Slot.java                           # Slot Entity
│   │   │   │   └── SlotRepository.java                 # Slot Data Access
│   │   │   │
│   │   │   ├── billing/                                # Billing Module
│   │   │   │   ├── BillingService.java                 # Billing Logic
│   │   │   │   ├── BillingRecord.java                  # Billing Entity
│   │   │   │   └── BillingRepository.java              # Billing Data Access
│   │   │   │
│   │   │   ├── notification/                           # Notification Module
│   │   │   │   └── NotificationService.java            # Notification Logic
│   │   │   │
│   │   │   ├── event/                                  # Event Definitions
│   │   │   │   ├── VehicleEnteredEvent.java            # Entry Event Record
│   │   │   │   └── VehicleExitedEvent.java             # Exit Event Record
│   │   │   │
│   │   │   └── config/                                 # Configuration
│   │   │       └── SwaggerConfig.java                  # Swagger/API Docs
│   │   │
│   │   └── resources/
│   │       ├── application.properties                  # App Configuration
│   │       ├── static/                                 # Static Files
│   │       └── templates/                              # HTML Templates
│   │
│   └── test/
│       └── java/org/devops/smartparkingsystem/
│           └── SmartParkingSystemApplicationTests.java # Unit Tests
│
├── pom.xml                                             # Maven Dependencies
├── ARCHITECTURE.md                                     # Architecture Diagram
├── README.md                                           # This File
└── HELP.md                                             # Spring Boot Help
```

---

## 🛠️ Prerequisites

Before running this project, ensure you have installed:

- **Java 17+** - [Download JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- **Maven 3.6+** - [Download Maven](https://maven.apache.org/download.cgi)
- **PostgreSQL 12+** - [Download PostgreSQL](https://www.postgresql.org/download/)

### Verify Installations

```bash
java -version
mvn -version
psql --version
```

---

## 📦 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/smart-parking-system.git
cd smart-parking-system
```

### Step 2: Create PostgreSQL Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE parkingDB;

# Create user (optional, if using custom credentials)
CREATE USER parkinguser WITH PASSWORD 'parkingpass';
ALTER ROLE parkinguser WITH CREATEDB;
GRANT ALL PRIVILEGES ON DATABASE parkingDB TO parkinguser;

# Exit PostgreSQL
\q
```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/parkingDB
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Port
server.port=8080
spring.application.name=smart-parking-system
```

### Step 4: Build the Project

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the source code
- Run unit tests
- Create an executable JAR

---

## 🚀 Running the Application

### Using Maven

```bash
# Run directly with Maven
mvn spring-boot:run
```

### Using JAR File

```bash
# Build JAR
mvn clean package

# Run the JAR
java -jar target/smart-parking-system-0.0.1-SNAPSHOT.jar
```

### Expected Output

```
Started SmartParkingSystemApplication in 3.456 seconds
Tomcat started on port(s): 8080
Swagger UI available at: http://localhost:8080/swagger-ui.html
```

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080
```

### 1. Vehicle Entry Endpoint

**Register a vehicle entering the parking lot**

```http
POST /api/entry
Content-Type: application/x-www-form-urlencoded

vehicleNumber=ABC123
```

**Request Example (curl)**
```bash
curl -X POST "http://localhost:8080/api/entry?vehicleNumber=ABC123"
```

**Response**
```
200 OK
vehicle enteredABC123
```

---

### 2. Vehicle Exit Endpoint

**Register a vehicle exiting and calculate charges**

```http
POST /api/exit
Content-Type: application/x-www-form-urlencoded

vehicleNumber=ABC123
```

**Request Example (curl)**
```bash
curl -X POST "http://localhost:8080/api/exit?vehicleNumber=ABC123"
```

**Response**
```
200 OK
vehicle exitedABC123
```

**Console Output**
```
Notification vehicle exited ABC123
Billed: 20.0 (minimum charge)
```

---

## ⚙️ Configuration

### Application Properties

| Property | Value | Description |
|----------|-------|-------------|
| `server.port` | 8080 | Server port |
| `spring.datasource.url` | jdbc:postgresql://localhost:5432/parkingDB | Database URL |
| `spring.datasource.username` | postgres | Database user |
| `spring.datasource.password` | postgres | Database password |
| `spring.jpa.hibernate.ddl-auto` | update | Auto-create/update database schema |
| `spring.jpa.show-sql` | true | Log SQL queries |

### Environment Overrides

You can override properties via environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/parkingDB
export SPRING_DATASOURCE_USERNAME=produser
export SPRING_DATASOURCE_PASSWORD=prodpass

mvn spring-boot:run
```

---

## 💡 How It Works

### Vehicle Entry Flow

```
1. User calls: POST /api/entry?vehicleNumber=ABC123
   ↓
2. EntryController receives request
   ↓
3. EntryService.vehicleEntry() executes:
   - Creates new ParkingEntry record
   - Sets entry time to current time
   - Sets active = true
   ↓
4. Publishes VehicleEnteredEvent
   ↓
5. Two subscribers react:
   a) NotificationService: Logs "Vehicle ABC123 entered at [time]"
   b) Database: Saves ParkingEntry to parking_entry table
   ↓
6. Response: "vehicle enteredABC123"
```

### Vehicle Exit Flow

```
1. User calls: POST /api/exit?vehicleNumber=ABC123
   ↓
2. ExitController receives request
   ↓
3. ExitService.vehicleExit() executes:
   - Fetches ParkingEntry from database
   - Sets exit time to current time (BUGFIX: was setting entry time)
   - Sets active = false
   ↓
4. Publishes VehicleExitedEvent with entry/exit times
   ↓
5. Two subscribers react:
   a) BillingService:
      - Calculates duration between entry and exit
      - Calculates charge: max(₹20, duration_in_hours * ₹50)
      - Saves BillingRecord to database
      - Logs "Billed: ₹[amount]"
   
   b) NotificationService:
      - Logs "Vehicle ABC123 exited"
   ↓
6. Response: "vehicle exitedABC123"
```

### Event Flow Diagram

```
┌──────────┐
│ Service  │
└────┬─────┘
     │ Publishes Event
     ▼
┌──────────────────────────┐
│ ApplicationEventPublisher│
│ (Spring Event System)    │
└────┬───────────────┬─────┘
     │               │
     │ Event 1       │ Event 2
     ▼               ▼
┌─────────────┐  ┌──────────────────┐
│ Listener A  │  │  Listener B      │
│ @EventList  │  │  @EventListener  │
└─────────────┘  └──────────────────┘
```

---

## 📊 Event-Driven Flow

### VehicleEnteredEvent

```java
public record VehicleEnteredEvent(
    String vehicleNumber,
    LocalDateTime entryTime
)
```

**Published by**: `EntryService.vehicleEntry()`  
**Subscribers**:
- `NotificationService.notifyOnVehicleEntry()`

### VehicleExitedEvent

```java
public record VehicleExitedEvent(
    String vehicleNumber,
    LocalDateTime entryTime,
    LocalDateTime exitTime
)
```

**Published by**: `ExitService.vehicleExit()`  
**Subscribers**:
- `BillingService.handleVehicleExit()` - Calculates charges
- `NotificationService.notifyOnVehicleExit()` - Sends notifications

---

## 🧩 Modules

### 📍 Entry Module (`entry/`)
**Responsibility**: Vehicle entry/exit operations

| Class | Purpose |
|-------|---------|
| `EntryController` | REST endpoints for entry/exit |
| `EntryService` | Business logic for vehicle entry |
| `ExitService` | Business logic for vehicle exit |
| `ParkingEntry` | JPA Entity (parking_entry table) |
| `ParkingEntryRepository` | Database access for parking entries |

### 💰 Billing Module (`billing/`)
**Responsibility**: Calculate and record parking charges

| Class | Purpose |
|-------|---------|
| `BillingService` | Event listener, calculates charges when vehicle exits |
| `BillingRecord` | JPA Entity (billing_record table) |
| `BillingRepository` | Database access for billing records |

**Pricing Logic**:
- Base fee: ₹20 (minimum)
- Additional: ₹50 per hour
- Formula: `max(20, (duration_in_hours) * 50)`

### 🔔 Notification Module (`notification/`)
**Responsibility**: Send entry/exit notifications

| Class | Purpose |
|-------|---------|
| `NotificationService` | Event listeners for entry and exit notifications |

**Current Implementation**: Console logging (easily extended to email/SMS)

### 🅰️ Allocation Module (`allocation/`)
**Responsibility**: Manage parking slots

| Class | Purpose |
|-------|---------|
| `Slot` | JPA Entity (slot table) |
| `SlotRepository` | Database access for slots |

**Schema**: 3 default slots created on startup (A1, A2, A3)

### ⚡ Event Module (`event/`)
**Responsibility**: Event definitions

| Class | Purpose |
|-------|---------|
| `VehicleEnteredEvent` | Event published on vehicle entry |
| `VehicleExitedEvent` | Event published on vehicle exit |

---

## 🐛 Bug Fixes

### Issue 1: Exit Time Not Being Set Correctly

**File**: `ExitService.java`

**Problem**:
```java
// ❌ WRONG - Sets entry time instead of exit time
entry.setEntryTime(LocalDateTime.now());
```

**Fix**:
```java
// ✅ CORRECT - Sets exit time
entry.setExitTime(LocalDateTime.now());
```

**Impact**: Exit time was not being recorded, making billing calculations fail.

---

### Issue 2: Event Parameter Name Typo

**File**: `VehicleExitedEvent.java`

**Problem**:
```java
// ❌ WRONG - Typo in parameter name (exitTIme)
public record VehicleExitedEvent(
    String vehicleNumber,
    LocalDateTime entryTime,
    LocalDateTime exitTIme  // ← Typo
)
```

**Fix**:
```java
// ✅ CORRECT - Proper camelCase (exitTime)
public record VehicleExitedEvent(
    String vehicleNumber,
    LocalDateTime entryTime,
    LocalDateTime exitTime  // ← Fixed
)
```

**Impact**: Compilation would fail due to method name mismatch in BillingService.

---

### Issue 3: BillingService Referencing Wrong Event Field

**File**: `BillingService.java`

**Problem**:
```java
// ❌ WRONG - Calls wrong method name (typo from event)
var duration = Duration.between(
    vehicleExitedEvent.entryTime(),
    vehicleExitedEvent.exitTIme()  // ← Wrong method name
);
```

**Fix**:
```java
// ✅ CORRECT - Calls correct method name
var duration = Duration.between(
    vehicleExitedEvent.entryTime(),
    vehicleExitedEvent.exitTime()  // ← Correct
);
```

**Impact**: Billing calculations would fail at runtime.

---

## 🔧 Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 4.0.5 | Web framework & application runtime |
| **Spring Modulith** | 2.0.5 | Modular architecture support |
| **Spring Data JPA** | (latest) | ORM & database access |
| **Hibernate** | (latest) | JPA implementation |
| **PostgreSQL** | 12+ | Relational database |
| **Java** | 17+ | Programming language |
| **Maven** | 3.6+ | Build & dependency management |
| **Lombok** | (latest) | Reduce boilerplate code |
| **Swagger/Springdoc** | (latest) | API documentation |

---

## 🧪 Testing

Run the unit tests:

```bash
mvn test
```

---

## 📝 Logging

The application uses Spring's built-in logging. Console output includes:

```
Entry Event:
Notification ABC123 entered at 2024-04-06T10:30:45.123456

Exit Event:
notification vehicle exited ABC123
Billed 50.0
```

To enable more detailed logging, update `application.properties`:

```properties
logging.level.root=INFO
logging.level.org.devops.smartparkingsystem=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.data=DEBUG
```

---

## 🚀 Future Enhancements

- [ ] Integrate real SMS/Email notifications
- [ ] Add Admin dashboard
- [ ] Implement role-based access control (RBAC)
- [ ] Add pagination to API endpoints
- [ ] Create comprehensive test suite
- [ ] Deploy with Docker & Kubernetes
- [ ] Add Prometheus metrics
- [ ] Implement caching layer (Redis)
- [ ] Add API rate limiting
- [ ] Extract modules as separate microservices

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Submit a pull request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Support

For questions or issues:
- Open an issue on GitHub
- Check existing documentation in `ARCHITECTURE.md`
- Review Spring Boot documentation: https://spring.io/projects/spring-boot

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Modulith Guide](https://spring.io/projects/spring-modulith)
- [Spring Data JPA Reference](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [REST API Best Practices](https://restfulapi.net/)

---

**Last Updated**: April 6, 2026  
**Version**: 1.0.0  
**Status**: Production Ready ✅

---

<div align="center">

### Made with ❤️ by the DevOps Team

**Give us a ⭐ if you found this helpful!**

</div>
