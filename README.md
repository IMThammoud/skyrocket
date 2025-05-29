![Alt-Text](src/main/resources/static/imgs/logo-header-hippo.svg)
### An Inventory App  (Codename Skyrocket)
Mister-Unternehmer (Skyrocket) ist eine WebApp, die dich bei deinem Unternehmertum unterstützt.
Sie ist auf Unternehmer ausgerichtet, die mehrere Tools an einem Ort haben möchten.
Geplant für die vollständige Veröffentlichung sind:
- Inventarfuntkion
- Rechnungserstellung (Mit & ohne Konto)
- Hilfe in Steuern und Finanzen
- PDF-integration von erstellen von Artikelauflistungen oder Regalen
- Generierte Links die den aktuellen Stand des Lagers spiegeln und als HTML-Seite oder PDF zurückgegeben werden, um diesen weiterzugeben oder zu teilen.
- Erstellen eigener Artikeltypen

Mehr auf der [Website](http://mister-unternehmer.de)

### Requirements to run locally:
- MariaDB Container using these Envs: MARIADB_USER, MARIADB_PASSWORD, MARIADB_ROOT_PASSWORD, MARIADB_DATABASE
- These application properties:
```
spring.datasource.driver-class-name= DB-Driver
spring.datasource.name= mariadb-database
spring.datasource.username= mariadb-user
spring.datasource.password= mariadb-password
spring.datasource.url= URL
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto= ddlmode
```
- Always make sure that there is a directory "pdf" at the root path "/"


#### Functionalities in progress:
+ Inventory with different shelves, articles and services
+ Exporting as csv, pdf or similar
+ Displaying Item/Article/Service as View
+ functioning login system
+ an API connection (eBay or PayPal or something similar)

#### ORM Migration in progress
- ~~Registration done~~
- ~~SessionID check on navigation done~~
- ~~Make Shelve creation work~~
- ~~listing shelves with new endpoints~~
- ~~Make Article creation work~~
- ~~Show article count in shelve view~~
- ~~listing articles with new endpoints on the dashboard~~