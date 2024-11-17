![Alt-Text](src/main/resources/static/imgs/logo-600px.svg)
### An Inventory App  (Codename Skyrocket)

### Requirements to run locally:
- Run a MariaDB-Database and call it skyrocket
- Create a file that stores environment variables -> "envs.txt" and put it in same dir as DatabaseConnector.class
- Variables should be: MARIADB_DATABASE, MARIADB_USER, MARIADB_PASSWORD or equal the Placeholders of the DatabaseConnector.class
- Try to run the project, the logs will tell you if DB-Connection was successful.


#### Functionalities in progress:
+ Inventory with different shelves, articles and services
+ functioning login system
+ an API connection (eBay or PayPal or something similar)