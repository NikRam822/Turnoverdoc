# Turnoverdoc

Turnoverdoc is a system for tax refund returns in the United Kingdom. The users of the system are people who work in the UK and want to claim tax refunds.

## Technologies

The project uses the following technologies:

### Backend
- **Java**
- **Spring Framework**
  - Spring Boot
  - Spring Security
  - Spring Data JPA
- **PostgreSQL**

### Frontend
- **Angular**
- **Akita Store**

## Installation and Running

### Backend

1. Ensure you have Java 11 and Maven installed.
2. Clone the repository:
  ```
   git clone https://github.com/NikRam822/Turnoverdoc.git
  ```
3. Navigate to the project directory:
```
cd Turnoverdoc
```
4. Set up the PostgreSQL database:
Create a database named turnoverdoc.
Configure the database connection parameters in the src/main/resources/application.properties file.

Example application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=MyPassword
spring.jpa.generate-ddl=true

spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

#SWAGGER
spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER

jwt.token.secret=mySecret
jwt.token.expired=3600000

spring.servlet.multipart.max-file-size = 15MB
spring.servlet.multipart.max-request-size = 150MB

spring.mail.host=myhost.ru
spring.mail.username=my.mail@mail.com
spring.mail.password=MyPassword
spring.mail.port=465
spring.mail.protocol=smtps
mail.debug=true

upload.path=path/to/upload/docs
```
5. Build and run the project:
```
mvn clean install
mvn spring-boot:run
```

#### LOGGING
To configure the location of the log file, change in logback.xml:
```
<file>src/main/resources/logs/turnoverdoc.txt</file>
```

#### SWAGGER
You can use a swagger to send requests to the server.

After starting the server, go to the page:
```
http://localhost:8080/swagger-ui.html
````


### Frontend
1. Ensure you have Node.js and Angular CLI installed.
2. Navigate to the frontend directory:
```
cd Frontend
```
3. Install the dependencies:
```
npm install
```
4. Run the frontend:
```
ng serve
```

#### Usage
After starting both the backend and frontend, open your browser and go to http://localhost:4200 to use the system.

## Project Structure

### Backend
- `src/main/java/com/turnoverdoc` - Main backend code.
  - `controller` - REST API controllers.
  - `service` - Business logic.
  - `repository` - Database repositories.
  - `model` - Data models.
  - `security` - Security configuration.

### Frontend
- `src/app` - Main frontend code.
  - `components` - Angular components.
  - `services` - Services for API interaction.
  - `store` - Application state managed with Akita.

