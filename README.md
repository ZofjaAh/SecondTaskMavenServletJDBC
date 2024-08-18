# REST API for Managing Students, Coordinators, and Courses

This project provides a REST API for managing students, coordinators, and courses. The API supports CRUD operations for each entity.

## Requirements
- JDK 17
- Apache Maven 3.8.1 or higher
- Docker 3.8 or higher


## Installation
1. Clone the repository:
    ```bash
    git clone <URL of your repository>
    ```
2. Navigate to the project directory:
    ```bash
    cd task-Rest-JDBC
    ```
3. Build the project using Maven:
    ```bash
    mvn clean install
    ```
    
## Running with Docker
1. Ensure Docker is installed and running on your machine.
2. Create and start the Docker containers:
    ```bash
    docker-compose up --build
    ```
3. The application will be accessible at `http://localhost:8082`.

### Docker Configuration
The `docker-compose.yml` file is configured as follows:
```yaml
version: "3.8"
services:
  db:
    image: postgres:15.0
    hostname: db
    container_name: maven_servlet_jdbc
    ports:
      - "5432:5432"
    volumes:
      - ./src/main/resources/db/V2_1__init_maven_servlet_jdbc_coordinator.sql:/docker-entrypoint-initdb.d/V2_1__init_maven_servlet_jdbc_coordinator.sql
      - ./src/main/resources/db/V2_2__init_maven_servlet_jdbc_course.sql:/docker-entrypoint-initdb.d/V2_2__init_maven_servlet_jdbc_course.sql
      - ./src/main/resources/db/V2_3__init_maven_servlet_jdbc_student.sql:/docker-entrypoint-initdb.d/V2_3__init_maven_servlet_jdbc_student.sql
      - ./src/main/resources/db/V2_4__init_maven_servlet_jdbc_student_course.sql:/docker-entrypoint-initdb.d/V2_4__init_maven_servlet_jdbc_student_course.sql
    networks:
      - backend
    restart: always
    environment:
      POSTGRES_DB: maven_servlet_jdbc
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    healthcheck:
      test: [ "CMD-SHELL", "pg_isready -U postgres" ]
      interval: 10s
      timeout: 5s
      retries: 5
  tomcat:
    depends_on:
      - db
    links:
      - db
    build:
      context: .
      dockerfile: Dockerfile
    volumes:
      - ./logs:/usr/local/tomcat/logs
    ports:
      - '8082:8080'
    environment:
      POSTGRES_URL: jdbc:postgresql://db/maven_servlet_jdbc
      POSTGRES_DB: maven_servlet_jdbc
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    networks:
      - backend
volumes:
  logs:
    driver: local
networks:
  backend:
```

### Dockerfile
The Dockerfile is configured as follows:

```yaml
FROM tomcat:10.1.28-jdk17-temurin-jammy
COPY src/main/resources/db.properties /usr/local/tomcat/conf/db.properties
COPY target/taskRestJDBC.war /usr/local/tomcat/webapps/taskRestJDBC.war
```
##  Dependencies
The project uses the following main dependencies:
- **PostgreSQL**: Driver for connecting to the PostgreSQL database.
- **Jakarta Servlet API**: For working with servlets.
- **Lombok**: To simplify code writing.
- **Logback**: For logging.
- **Jackson Databind**: For working with JSON.
- **JSON Simple**: For simple JSON parsing.
- **JUnit Jupiter**: For writing tests.

 ## Maven Plugins
The project uses the following Maven plugins:
- **maven-compiler-plugin**: For compiling the source code.
- **maven-war-plugin**: For packaging the project as a WAR file.
- **maven-surefire-plugin**: For running tests.
- **maven-javadoc-plugin**: For generating documentation.

## API Endpoints
### Coordinator
- Create Coordinator
 ```bash
POST http://localhost:8082/taskRestJDBC/rest/coordinator
```
Body: JSON 
 ```bash
{"name": "Coordinator1"}
```
Response: JSON
```bash
{"coordinator_id": "1"}
```
- Update Coordinator Name
 ```bash
PUT http://localhost:8082/taskRestJDBC/rest/coordinator/1
```
Body: JSON 
 ```bash
{"name": "NewCoordinator"}
```
- Get All Coordinators
 ```bash
GET http://localhost:8082/taskRestJDBC/rest/coordinators
```
Response: JSON
```bash
[{"id":1,"name":"Ylian","students":null}]
```
- Get Coordinator By Id
 ```bash
GET http://localhost:8082/taskRestJDBC/rest/coordinator/1
```
Response: JSON
```bash
{"id":1,"name":"Ylian","students":[{"id":1,"name":"Student1"},{"id":3,"name":"Student3"}]}
```
- Delete Coordinator By Id
 ```bash
DELETE http://localhost:8082/taskRestJDBC/rest/coordinator/1
```
### Course
- Create Course
 ```bash
POST http://localhost:8082/taskRestJDBC/rest/course
```
Body: JSON 
 ```bash
{"name": "Course1"}
```
Response: JSON
```bash
{"course": "1"}
```
- Update Course Name
 ```bash
PUT http://localhost:8082/taskRestJDBC/rest/course/1
```
Body: JSON 
 ```bash
{"name": "NewCourse"}
```
- Get All Courses
 ```bash
GET http://localhost:8082/taskRestJDBC/rest/courses
```
Response: JSON
```bash
[{"id":1,"name":"Singing","students":null},{"id":2,"name":"Stinging","students":null}]
```
- Delete Course By Id
 ```bash
DELETE http://localhost:8082/taskRestJDBC/rest/course/1
```
### Student
- Create Student
 ```bash
POST http://localhost:8082/taskRestJDBC/rest/student
```
Body: JSON 
 ```bash
{"name": "Student1",
"coordinatorId": "1"}
```
Response: JSON
```bash
{"student_id": "1"}
```
- Add Course For Student
 ```bash
POST http://localhost:8082/taskRestJDBC/rest/student_course/1
```
Body: JSON 
 ```bash
{"courseId": "1"}
```
- Update Coordinator For Student 
 ```bash
PUT http://localhost:8082/taskRestJDBC/rest/student/1
```
Body: JSON 
 ```bash
{"coordinatorId": "2"}
```

- Get Student By Id
 ```bash
GET http://localhost:8082/taskRestJDBC/rest/student/1
```
Response: JSON
```bash
{"id":1,"name":"Student1","coordinatorId":1,"courses":[]}
```
or With Courses 
```bash
{"id":1,"name":"Student1","coordinatorId":1,"courses":[{"id":1,"name":"Student1"},{"id":2,"name":"Student1"}]}

```

- Delete Student By Id
 ```bash
DELETE http://localhost:8082/taskRestJDBC/rest/student/1
```




                                                                  

