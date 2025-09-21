![Alt-Text](src/main/resources/static/imgs/logo-header-hippo.svg)

Available on: [mister-unternehmer.de](https://mister-unternehmer.de)

An Inventory App (Codename Skyrocket)
Mister-Unternehmer (Skyrocket) is a web app designed to support you in your entrepreneurial journey. It is aimed at entrepreneurs who want to have multiple tools in one place. The full release is planned to include inventory functionality, invoice generation (with and without an account), assistance with taxes and finances, PDF integration for creating item or shelf listings, generated links that reflect the current stock status and can be returned as an HTML page or PDF for sharing, and the ability to create custom item types. (Website currently down).

To run the app locally, you need a MariaDB container with the following environment variables: MARIADB_USER, MARIADB_PASSWORD, MARIADB_ROOT_PASSWORD, and MARIADB_DATABASE. Additionally, the following application properties must be set:

Properties file for db connection:

spring.datasource.driver-class-name= DB-Driver  
spring.datasource.name= mariadb-database  
spring.datasource.username= mariadb-user  
spring.datasource.password= mariadb-password  
spring.datasource.url= URL  
spring.jpa.show-sql=true  
spring.jpa.hibernate.ddl-auto= ddlmode  

**Make sure there is always a directory named pdf at the root path where the application is ran from: /.**

