# Quizzzy - challenge yourself

This is the backend service for quiz game.

## Setup and Running the Project

### Prerequisites

You would need the following tools installed before running the project locally:

- Java 17
- Maven
- IntelliJ IDEA (or any preferred IDE)
- Docker

### Running the project

1. Create .env file in the root folder with database credentials:
   ```
   QZ_DATABASE_NAME=quizzzy-db
   QZ_DATABASE_USER=user
   QZ_DATABASE_PASSWORD=DB-password
   QZ_DATABASE_ROOT_PASSWORD=DB-password
   JWT_SECRET=36763979244226452948404D635166546A576D5A7134743777217A25432A462D
   ```
2. Start DB
    - run `docker-compose up -d` in a terminal in the root folder
        - This command will start a mysql DB in a docker container with the properties we've entered in the .env file
3. Setup IntelliJ environment variables
    - Run -> Edit Configurations, then under Environment Variables, you should add the following:
   ```
   QZ_DATABASE_NAME=quizzzy-db;QZ_DATABASE_PASSWORD=DB-password;QZ_DATABASE_ROOT_PASSWORD=DB-password;QZ_DATABASE_USER=user;JWT_SECRET=36763979244226452948404D635166546A576D5A7134743777217A25432A462D
   ```
   The first 4 parameters are responsible for database connection and should match the ones we set up in
   step 1. The
   last one is a key for JWT token encoding. You can choose to use a different one.

4. Start the app
    - run `mvn clean install` in a terminal to get all the needed dependencies and to build the project
    - Run -> Run -> choose the configuration you set up in step 4
        - The app should be running on localhost:8080

5. Interact with the app

   In this application is implemented one ROLE_ADMIN user and one ROLE_USER user.
   The next few lines describe how these users can be accessed.
    - admin user -> email: admin@abv.bg password: admin123@@
    - test user ->  email: test@abv.bg password: test123@@

6. Api documentation:
    - http://localhost:8080/auth/swagger-ui/index.html#/

7. Mail sender: To be able to send emails through the application, you need to enter your environment variables for
   gmail username and password
    - QZ_MAIL_USERNAME=
    - QZ_MAIL_PASSWORD=