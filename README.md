# Project Setup and Run Instructions

This README provides step-by-step instructions to set up and run the project.

## Prerequisites

Before you begin, ensure you have the following installed:

- Git
- JDK >21
- Docker
- Node.js<v18.19 and npm 
- Angular CLI = 18.2.6

## Environment Setup

1. Clone the repository:
```git clone https://github.com/dbal0503/MightyElephants```
2. Set the JAVA_HOME environment variable to point to your JDK 23 installation.

## Backend Setup

1. Navigate to the backend directory:
```cd backend```
2. Start the Docker containers:
```docker-compose up --build```
3. Build the project:
For windows:
```./mvnw.cmd clean package```
For Linux/MacOs
```./mvnw clean package```
5. Run the backend application:
```java -jar target/backend-0.0.1-SNAPSHOT.jar```

## Frontend Setup

1. Navigate to the frontend directory from root:
```cd /frontend```
2. Install dependencies:
```npm i```
3. Ensure Angular CLI is installed globally:
```npm i -g @angular/cli```
4. Start the Angular development server:
ng serve

## Accessing the Application

Open your web browser and navigate to `http://localhost:4200` to access the application.

You should now be able to reproduce the login functionality.
