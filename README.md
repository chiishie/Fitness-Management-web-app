# Fitness-Management-Web-App

## Overview
**Fitness-Management-Web-App** is a comprehensive fitness and health tracking application built using **Java (Spring Boot)** for backend services, **MySQL** for database storage, and **Thymeleaf** for templating. This app is designed to help users manage and track their fitness goals by logging exercises, food intake, health metrics, and more.

## Key Features

### 1. User Management
- **User Registration/Login**: Users can register with essential details and log in securely to access their data.
- **Profile Management**: Allows users to view and update their profile information, including basic health metrics like weight and BMI history.

### 2. Exercise Tracking
- **Record Exercises**: Log various exercises with specific details like duration and intensity.
- **View Exercise History**: Track previous workouts and see total calories burned over time.

### 3. Food Tracking (Using USDA API)
- **Search and Log Food**: Users can search for food items using the USDA API, view nutritional data, and log daily intake.
- **View Food History**: Displays a historical breakdown of daily calorie and nutrient intake.

### 4. Health Data Management
- **BMI Calculation**: Automatic BMI calculation based on user weight and height, with historical tracking.
- **Health Metrics Tracking**: Allows logging of vital metrics like weight, blood pressure, and heart rate.

### 5. Dashboard and Analytics
- **Dashboard Overview**: Summarizes recent activities, food intake, and health metrics.
- **Analytics & Insights**: Provides insights and suggestions on fitness trends and goals.

### 6. Notifications and Reminders
- **Exercise Reminders**: Users can opt for reminders to stay on track with their workouts.
- **Health Metric Reminders**: Prompts to update health metrics regularly.

### 7. Data Persistence and Security
- **Database Storage**: MySQL stores user profiles, exercise logs, food history, and health data.
- **Secure Authentication**: User credentials are securely managed, ensuring data privacy.

### 8. Additional Features
- **Goal Setting**: Set and track progress toward personal fitness and health goals.
- **Data Export**: Export exercise, food, and health data for offline use or sharing.
- **Social Sharing**: Optionally share progress and achievements on social platforms.

## Technology Stack
- **Backend**: Java with Spring Boot for backend services.
- **Database**: MySQL for relational data storage.
- **Frontend**: Thymeleaf (templating engine) with optional JavaScript enhancements.
- **API Integration**: USDA API for nutritional information.

## Getting Started

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/Fitness-Management-web-app.git
   cd Fitness-Management-web-app
   ```

2. **Configure the Database**:
   - Set up a MySQL database and update the database configuration in `application.properties`.

3. **Run the Application**:
   - Run the app using Spring Boot:
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Access the App**:
   - Open a browser and go to `http://localhost:8080` to start using the app.

## License
This project is licensed under the MIT License.
