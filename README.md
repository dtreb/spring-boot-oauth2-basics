# Spring (Boot, Data, Security) with OAuth2 implementation.

### Info
This is a very simple project that demonstrates usage of:

 1. [Spring Boot](https://projects.spring.io/spring-boot/) for project setup.
 2. [Simple Java Mail](https://github.com/bbottema/simple-java-mail) for emailing.
 3. [Swagger2](http://swagger.io/) for API description.
 4. Basic security tests with [Mockito](http://mockito.org/).
 5. [H2](http://www.h2database.com/html/main.html) as temporary storage. 
 6. [HikariCP](https://brettwooldridge.github.io/HikariCP/) as connection pool implementation.  
 7. [metrics-spring](https://github.com/ryantenney/metrics-spring) for endpoints, pool, memory and other metrics.  

### Setup:

 1. Adjust config at [mail.properties](./src/main/resources/mail.properties) with your mail credentials.   
 2. Adjust other settings at [application.properties](./src/main/resources/application.properties) if needed.
 2. Run application via
    ```$ mvn spring-boot:run```
 3. Then browse to: [http://localhost:8888/ping](http://localhost:8888/ping)   
 4. [Optional] Check [http://localhost:8888/metrics?pretty=true](http://localhost:8888/metrics?pretty=true) for endpoints metrics.  

#### API
> **Note:**  
> - Use [http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html) to get detailed info about every endpoint.   
> - Always check that you use OAuth2 pair **"dtreb:secret"** to obtain **"access_token"** and **"refresh_token"**.  
> - Use proper **"access_token"** retrieved from the authorization response as **"Authorization: Bearer CODE"** header for all **/api/...** requests.   

TYPE  | URL                                     | Parameters (^ - required)                                                                | Curl | Info |
----- | --------------------------------------- | --------------------------------------------------------------| ------------------------------------------------------------------------------------ | ----|
GET   |   /ping                                 |                                                               | ```curl http://localhost:8888/ping```|Ping|
GET   |   /oauth/token                          |^username: String, ^token: String, ^grant_type: "password"     | ```curl -vu dtreb:secret "http://localhost:8888/oauth/token" -d "username=user&password=user&grant_type=password"```| Authenticate (OAuth2).|
GET   |   /oauth/token                          |^refresh_token: String, ^grant_type: "refresh_token"           | ```curl -vu dtreb:secret "http://localhost:8888/oauth/token" -d "grant_type=refresh_token&refresh_token=12345"```|Get new token. Pass *refresh_token* to get new *access_token*.|
POST  |   /register                             |^login: String, ^password: String, ^email: String              | ```curl -X POST --data "login=den&password=den&email=den@mail.me" "http://localhost:8888/register"```|Register new user.|
POST  |   /activate                             |^activationKey: String, ^email: String                         | ```curl -X POST --data "activationKey=12345&email=den@mail.me" "http://localhost:8888/activate"```|Activate user.|
POST  |   /lostPassword                         |^email: String                                                 | ```curl -X POST --data "email=den@mail.me" "http://localhost:8888/lostPassword"```|Searches existing user by email, sets *resetPasswordKey* for it. Assumes /resetPassword call afterwards.|
POST  |   /resetPassword                        |^email: String, ^newPassword: String, ^resetPasswordKey: String| ```curl -X POST --data "email=den@mail.me&newPassword=den1&resetPasswordKey=12345" "http://localhost:8888/resetPassword"```| Uses *resetPasswordKey* to update user's password.|
GET   |   /api/user                             |                                                               | ```curl -H "Authorization: Bearer 12345" "http://localhost:8888/api/user"```|Get current user info.|

Feel free to use, comment or collaborate.
