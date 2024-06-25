# Spring Boot Lookup

This page is based on a tutorial by the famous Chad the Tutor. To make it run please execute the following steps:

1. Install maven
2. Clone the repository
3. Run maven install `mvn install`
4. Execute the steps to set up the Postgres DB
5. Go to the folder `gravel` and run the command `mvn spring-boot:run`


## POSTGRES setup for JDBC/hibernate

First Install [postgresql](https://www.postgresql.org/download/) and check installation from your console with the command `psql --version` (keep your password in mind).


### For Customization (optional)
1. Log in as `postgres` user with `psql -U postgres` and create a database with `CREATE DATABASE <db-name>;`
2. Create a new role with limited permissions for spring boot with `CREATE ROLE <role-name> WITH PASSWORD '<password>';`
3. Give log in permissions to the new role with `ALTER ROLE <role-name> WITH LOGIN;`
4. Grant privileges to new role with `GRANT CREATE, DROP ON DATABASE <db-name> TO <role-name>;`
5. Update the SQL scripts to match your requirements


### SQL setup scripts
To set up all the tables for this application it is recommended to use the scripts in numeric order.

1. You can open a CLI and navigate to the repo folder to execute `psql -U postgres -f .\01_setup-data.sql`
2. If the data script was successful you can execute `psql -U postgres -f .\02_setup-security.sql`


### Run the application

Either you can use the embedded version of maven running `.\gravel\mvnw.cmd spring-boot:run` or you can install maven, go to the `gravel` directory and run `mvn spring-boot:run`.
