# User Service 🌟

The User Service manages user-related operations and provides information about user ratings for various hotels. This service fetches data from the Hotel Service and Rating Service using Feign clients and retrieves configuration from GitHub.

## Quick Start 🚀

1. **Clone Repository:**
   ```bash
   git clone https://github.com/yourusername/userservice.git

2. **Database Configuration:**
  - Update the `application.properties` file with your database credentials.
  - Specify the database URL, username, and password.
    ```bash
    # Database Configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
    spring.datasource.username=your_database_username
    spring.datasource.password=your_database_password

3. **Run User Service:**
    ```bash
    cd userservice
    ./mvnw clean package
    java -jar target/userservice-1.0.0.jar

4. **Access User Service:**
   - Visit http://localhost:8081/users to interact with the User Service.

## GitHub Configuration ⚙️

The User Service fetches configuration from the GitHub repository:
- **GitHub Configuration Repository:**
    - https://github.com/harshil-padasala/config-profiles-HotelRatingProject

## API Endpoints 🛣️

### Create User 🚪

- **Endpoint:**
  ```bash
  POST http://localhost:8081/users

- **Request Body:**
  ```bash
  {
  "name": "User Name",
  "email": "user@example.com",
  "about": "User's description"
  }

### Get User by ID 🔍

- **Endpoint:**
  ```bash
  GET http://localhost:8081/users/{userId}

- **Response:**
  ```bash
  {
  "userId": "uniqueUserId",
  "name": "User Name",
  "email": "user@example.com",
  "about": "User's description"
  }

### Get All Users 🧑‍🤝‍🧑

- **Endpoint:**
  ```bash
  GET http://localhost:8081/users

- **Response:**
  ```bash
  [
  {
    "userId": "uniqueUserId1",
    "name": "User Name 1",
    "email": "user1@example.com",
    "about": "User 1's description"
  },
  // ... (other users)
  ]

### Delete User 🗑️

- **Endpoint:**
  ```bash
  DELETE http://localhost:8081/users/{userId}

- **Response:**
  ```bash
  {
  "userId": "deletedUserId",
  "name": "Deleted User",
  "email": "deleteduser@example.com",
  "about": "User's description"
  }

### Update User 🔄

- **Endpoint:**
  ```bash
  PUT http://localhost:8081/users/{userId}

- **Request Body:**
  ```bash
  {
  "userId": "uniqueUserId",
  "name": "Updated User Name",
  "email": "updateduser@example.com",
  "about": "Updated User's description"
  }

- **Response:**
  ```bash
  {
  "userId": "uniqueUserId",
  "name": "Updated User Name",
  "email": "updateduser@example.com",
  "about": "Updated User's description"
  }




