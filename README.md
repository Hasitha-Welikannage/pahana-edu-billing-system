# Pahana Edu – Online Billing System

## 📄 Project Description
The Pahana Edu – Online Billing System is a Java-based web application developed to automate the billing process for a bookshop.  
It replaces manual billing with a secure, efficient, and user-friendly online platform, enabling smooth customer, item, and bill management.

The system follows a three-tier architecture:
- Presentation Layer: REST APIs (JAX-RS, Jakarta EE)
- Service Layer: Business logic and validations
- Data Access Layer (DAO): Database interactions

---

## 🎯 Project Goals
- Eliminate manual billing inefficiencies
- Improve accuracy of sales and inventory data
- Provide secure and role-based access
- Enable quick retrieval of bill history
  
---

## ✨ Features
- User Authentication (Login/Logout)
- Customer Management (Add/Edit/Delete)
- Item Management (Add/Edit/Delete)
- Bill Creation with multiple items
- Bill History with detailed view and print option
- Role-based Access (Admin/User)
- Validation and Error Handling

---

## 📦 Modules Implemented
1. User Management
2. Customer Management
3. Item Management
4. Bill Creation
5. Bill History
6. Help Section

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL Database
- Netbeans IDE
- GlassFish Serverver 7
- Git
- Node js

### Steps
1. Clone the Repository
   ```bash
   git clone https://github.com/your-username/pahana-edu-billing-system.git
   
2.Import into IDE
 - Open in Netbeans
 - Configure as Maven project
   
3. Database Setup
 - Create MySQL database: CREATE DATABASE `pahana_billing`;
 - Update persistence.xml with your DB credentials

4. Run the application from the Netbeans IDE
   
5. For the front end open the front_end folder in vsCode then run "npm init" then "npm run dev"
