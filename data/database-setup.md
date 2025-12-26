### DDL - Data Definition Language

DDLs (Data Definition Language) for the stu course registration app
a subset of SQL used to define, create, modify, and delete database structures like tables, indexes, and views,
focusing on the database schema rather than the data within it, with commands like CREATE, ALTER, DROP, and TRUNCATE.

#### Create DB schema (regis_db is the name of the database/schema)

```sql
CREATE DATABASE regis_db;
USE regis_db;
```

#### Creating new application user

```sql
CREATE USER 'app_user'@'localhost' IDENTIFIED BY 'Kalaam@123';
```

#### Grant ALL permissions on this database to the newly created application user

```sql
GRANT ALL PRIVILEGES ON regis_db.* TO 'app_user'@'localhost';
```

#### Grant ONLY permissions on this database

```sql
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX
ON regis_db.* TO 'app_user'@'localhost';

FLUSH PRIVILEGES;
```

#### Verify if the current user exists

```sql
SELECT Host, User
FROM mysql.user
WHERE User = 'app_user';
```

#### Next steps
- Proceed to create the necessary tables using DDL scripts [regis_db_ddl.md](regis_db_ddl.md).
- Populate the tables with sample data using DML scripts [regis_db_dml.md](regis_db_dml.md).
- Setup following database connection parameters in the application configuration properties/yaml file: [application.properties](../src/main/resources/application.properties) 
  - Database URL: `jdbc:mysql://localhost:3306/regis_db`
  - Username: `app_user`
  - Password: `Kalaam@123`