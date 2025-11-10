# 🧠 Task, Project and Notification Management System

A **multi-organization task, project and notification management system** built using **Spring Boot (Backend)** with **JWT-based Authentication**, **Role-Based Authorization**, and an **intelligent Notification System**.  
The application enables organizations to manage projects, tasks, users and notifications efficiently while ensuring secure and scalable multi-tenant architecture.

---

## 🚀 Features

### 🧩 Core Features
- **User Management**
  - Create, update and manage users within organizations.
  - Passwords securely hashed using `BCryptPasswordEncoder`.
  - Role-based system: `ADMIN`, `MANAGER`, `EMPLOYEE`.

- **JWT Authentication**
  - Stateless authentication with JWT bearer tokens.
  - Role-based API access control.
  - Secure token validation using filters.

- **Organization Management**
  - Multi-tenant structure — each organization has its own users and projects.
  - Supports large-scale usage (500+ organizations).

- **Project & Task Management**
  - Projects can have multiple members.
  - Tasks can be assigned to multiple users.
  - CRUD APIs for projects and tasks with validation and role restrictions.

- **Notification System**
  - Trigger notifications on task or project updates.
  - Supports **instant**, **batched (digest)**, and **priority-based** notifications.
  - Retry mechanism for failed deliveries.
  - Handle 40k+ notification in an one go.

- **Device Tracking**
  - Stores user devices and push tokens.
  - Tracks `lastSeenAt` for real-time user status.

---

## 🧱 System Architecture

The platform follows a **two-tier layered architecture**:

### ⚙️ Application Layer (Backend)
- Developed using **Spring Boot**, with:
  - `Spring Security` for authentication & authorization.
  - `Spring Data JPA` for ORM.
  - `ModelMapper` for DTO conversions.
  - `Lombok` for Entity management.
  - `SLF4J` for debbuging.
  - `Swagger` for api documentation.
  - `BCryptPasswordEncoder` for password hashing.
- Contains business logic for:
  - User & Role Management  
  - Project/Task CRUD Operations  
  - Notification Creation & Processing  

### 🗄️ Data Layer (Database)
- Uses **MySQL** as the relational database.
- Maintains data isolation per organization.
- Indexed columns for performance (`email`, `organization_id`, etc.).

---

## ⚙️ Technologies Used

| Layer | Technology |
|--------|-------------|
| **Backend** | Spring Boot, Spring Security, JWT, Hibernate/JPA |
| **Database** | MySQL |
| **Authentication** | JWT Token-Based |
| **Encryption** | BCryptPasswordEncoder |
| **Mapping** | ModelMapper |
| **Language** | Java 17 |
| **Build Tool** | Maven |

---

## 🧠 Workflow Overview

### 🔐 Authentication Flow
1. User logs in with email & password.
2. System validates credentials using Spring Security.
3. Generates a **JWT Token** (includes email & role).
4. Token is sent to the client and stored (usually in localStorage).
5. Subsequent API requests include `Authorization: Bearer <token>` header.
6. Filter validates token and grants access based on role.

---

### 📋 Task/Project Operation Flow
1. Authenticated user performs CRUD operation on a task or project.
2. Event triggers a **Notification** (e.g., “Task completed by user X”).
3. System checks recipient preferences:
   - Instant → sent immediately (Push/Email).
   - Digest → queued for batch sending.
4. Notification is stored in DB with delivery attempts.
5. Failed deliveries are retried asynchronously.

---

### 🔔 Notification Lifecycle
```
Task Update
↓
Notification Created
↓
Preference Check (Instant / Digest)
↓
Delivery Attempt (Push / Email)
↓
Retry if Failed
↓
Persist in Notification DB
```

---

## 🧩 Entity Relationships Summary

| Entity | Relationship | Description |
|--------|---------------|-------------|
| **User** | Many-to-One → Organization | Each user belongs to an organization. |
|  | One-to-Many → Devices | A user can log in from multiple devices. |
|  | Many-to-Many → Tasks | User can be assigned multiple tasks. |
| **Organization** | One-to-Many → Projects & Users | Each organization has multiple projects and users. |
| **Project** | Many-to-Many → Users | Members of a project. |
|  | One-to-Many → Tasks | Each project contains multiple tasks. |
| **Task** | Many-to-Many → Users | Tasks can be assigned to multiple users. |
| **Notification** | One-to-Many → DeliveryAttempts | Tracks notification delivery attempts. |
| **UserNotificationPreference** | Many-to-One → User | Stores notification preferences for each user. |

---

## 🧩 System Flowchart
```
User Login
↓
JWT Token Generation
↓
Authorized Access
↓
CRUD Operations (Tasks / Projects)
↓
Event Trigger (Task Update / Project Assignment)
↓
Notification Creation
↓
User Preference Check (Instant / Digest)
↓
Delivery (Push / Email)
↓
Feedback / Retry if Failed
↓
Stored in Database
```


---

## 🔐 Role-Based API Access

| Role | Access |
|------|--------|
| **ADMIN** | Can manage organizations, users, projects, and tasks. |
| **MANAGER** | Can manage projects and assign tasks within their organization. |
| **EMPLOYEE** | Can view and update assigned tasks. |

APIs are protected with `@PreAuthorize` and JWT validation filters.

---

## 💾 Folder Structure
<img width="508" height="778" alt="Screenshot 2025-11-10 122849" src="https://github.com/user-attachments/assets/ee147164-47c9-4c1e-b7c3-c92aeb146218" />


---

## 🧩 Setup Instructions

### 🛠️ Prerequisites
- Java 17
- Maven 4.0.0+
- MySQL 8+
- Postman or any REST API client

## ~ Gaurav Kumar.








