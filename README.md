
# ElectronicStore - An E-commerce Web Application
Designed and developed a feature-rich Java-based e-commerce using Spring Boot, Spring JPA, and Spring Security for secure authentication and authorization. Leveraged Java 8 for core development and implemented an MVC architecture to enhance code modularity and maintainability for user, product, category, and order management. It offers users a seamless online shopping experience while also supporting user roles and optimizing for security, performance, and scalability

## Tools Used
- Spring Boot
- Spring Security
- Spring Data JPA
- Java
- MySQL
- Hibernate

## Test Project 
User Login endpoint 
http://localhost:9090/auth/login

![App Screenshot](https://github.com/vikasSharma311/ElectronicStore/assets/147338046/4d58db43-b164-4354-9d44-378e85a05055)


create User endpoint http://localhost:9090/users
![App Screenshot](https://github.com/vikasSharma311/ElectronicStore/assets/147338046/b311678c-1db6-4181-8f03-0f8f280ea210)


Get all users endpoint http://localhost:9090/users?pageNumber=0&pageSize=10&sortBy=name&sortDir=desc

![App Screenshot](https://github.com/vikasSharma311/ElectronicStore/assets/147338046/b35ca0d8-2978-40ec-babe-2ab70e4549cf)


Other Api for Users,Product,Category,Cart,Order are inside Electronic Store-Apis folder
## Future Work
.Api Documentaion using Swagger

.Dockerization

.Migration From Spring Boot 2.x to 3.x