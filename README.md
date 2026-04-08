# Smart Queue System (Banking Simulation)

## Overview

Smart Queue System is a mobile-based queue management platform designed to simulate real-world banking operations. The system aims to optimize customer flow, reduce waiting time, and provide real-time visibility into queue status.

This project models core banking queue processes such as multi-branch service handling, ticket generation, queue prioritization, and real-time updates.

---

## System Architecture

The system follows a client-server architecture:

- Mobile Client (Android) for customer interaction
- Backend Service (Spring Boot) for business logic and orchestration
- Relational Database (MySQL/PostgreSQL) for persistent storage
- WebSocket layer for real-time communication

---

## Core Features

### Customer (Mobile)
- Select branch and service type
- Generate queue ticket
- View real-time queue position
- Check estimated waiting time
- Book time slot (appointment-based queue)
- Cancel active ticket

### Officer (Admin)
- Monitor queue dashboard
- Call next ticket
- Manage service counters (open/close)

### System (Automated)
- Queue number generation
- Real-time queue broadcasting
- Push notification delivery

---

## Domain Model

### Branch
Represents a bank branch location.

- Basic information (name, location, address)
- Activation status

---

### Service
Defines available services per branch (e.g., Teller, Customer Service).

- Service type
- Queue prefix (e.g., T, CS)
- Daily queue limit
- Average service time

---

### Queue Session
Represents a daily queue lifecycle per service.

- Session date
- Current number
- Total served count
- Status

---

### Counter
Represents a physical service desk handled by an officer.

- Associated service
- Assigned officer
- Operational status

---

### Ticket
Core entity representing a customer's queue entry.

- Queue number
- Status lifecycle:
    - WAITING
    - CALLED
    - SERVED
    - COMPLETED
    - CANCELLED
- Priority level (NORMAL / PRIORITY)
- Position in queue
- Timestamp tracking:
    - booked_at
    - called_at
    - served_at
    - completed_at

---

### User
Represents system users.

- Customer and officer roles
- Authentication data (hashed password)

---

### Notification
Handles communication to users.

- Linked to ticket and user
- Message payload
- Delivery status

---

## Queue Processing Logic

### Ticket Generation
- Queue numbers are generated per service and session
- Prefix-based numbering strategy (e.g., T-001)

---

### Queue Ordering
- Dual-queue strategy:
    - Priority queue (VIP / special cases)
    - Normal queue
- Priority queue is always processed first if not empty

---

### Session-Based Queueing
- Each service operates within a daily queue session
- Queue state is isolated per session to ensure consistency

---

### Capacity Control
- Daily queue limit enforced at service level
- Slot-based booking controlled by quota

---

### Real-Time Updates
- WebSocket used to broadcast queue changes
- Clients subscribe to service-specific topics

---

## Scalability Considerations

- Multi-branch support with independent queue sessions
- Horizontal scaling possible with external message broker
- Database indexing on ticket and session for performance
- Stateless backend design for load balancing

---

## Project Goals

This project is designed to demonstrate:

- Backend system design using Spring Boot
- Real-time communication with WebSocket
- Queue management algorithms and priority handling
- Domain-driven design for enterprise-like systems
- Integration between mobile client and backend services

---

## Use Case

![btn_smart_queue_use_case.jpg](../../Users/ASUS/Downloads/btn_smart_queue_use_case.jpg)

---

## Design Table

![btn_smart_queue_erd.jpg](../../Users/ASUS/Downloads/btn_smart_queue_erd.jpg)

---