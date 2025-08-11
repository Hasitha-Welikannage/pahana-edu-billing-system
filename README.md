# Pahana Edu – Online Billing System

## 📄 Project Description
The Pahana Edu – Online Billing System is a secure and efficient full-stack web application designed to automate the billing process for a bookshop. It replaces manual billing with a user-friendly online platform for seamless management of customers, items, and bills. The application is built with a clear separation of concerns:

- Frontend: A modern, single-page application built with React.js.
- Backend: A robust RESTful API developed with Java 21 and Jakarta EE (JAX-RS).
- Database: A relational database managed by MySQL.

This architecture ensures modularity, scalability, and maintainability, with the frontend and backend communicating through standardized REST API calls.


## 🎯 Project Goals
- Eliminate manual billing inefficiencies
- Improve accuracy of sales and inventory data
- Provide secure and role-based access
- Enable quick retrieval of bill history
  

## ✨ Features
- User Authentication (Login/Logout)
- Customer Management (Add/Edit/Delete)
- Item Management (Add/Edit/Delete)
- Bill Creation with multiple items
- Bill History with detailed view and print option
- Role-based Access (Admin/User)
- Validation and Error Handling


## 📦 Technologies Used
1. Frontend: React.js, Node.js
2. Backend: Java 21+, Jakarta EE (JAX-RS)
3. Database: MySQL
4. Server: GlassFish Server 7+
5. Build Tools: Maven 3.8+ (Backend), npm (Frontend)
6. Version Control: Git


## 🚀 How to Run Locally

### Prerequisites
- Java Development Kit (JDK) 21+ (if using java 17 please update the pom.xml file with java 17 verson)
- Maven 3.8+
- MySQL Database
- Netbeans IDE
- GlassFish Serverver 7+
- Git
- Node.js & npm

### Backend Steps
1. Clone the Repository
   ```bash
   git clone https://github.com/Hasitha-Welikannage/pahana-edu-billing-system.git
   
2.Import into IDE: Open the project in NetBeans IDE and configure it as a Maven project.
3. Database Setup
 - Create MySQL database: CREATE DATABASE `pahana_billing`;(Does not need to create the table in the application run check if table exist and create them if not)
 - Update the database.properties file with your MySQL credentials.
4. Run Backend: Deploy and run the application on your GlassFish server from within NetBeans.

## Frontend Setup
1. Navigate to Frontend Directory:
2. Install Dependencies: npm install
3. Start the Frontend: npm run dev
