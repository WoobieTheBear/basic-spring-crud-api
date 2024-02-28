# POSTGRES setup for JDBC/hibernate

1. Install postgresql and check installation with `psql --version` (keep your password in mind)
2. Log in as `postgres` user with `psql -U postgres` and create a database with `CREATE DATABASE <db-name>;`
3. Create a new role with limited permissions for spring boot with `CREATE ROLE <role-name> WITH PASSWORD '<password>';`
4. Give new role right to log in with `ALTER ROLE <role-name> WITH LOGIN;`
5. Grant privileges to new role with `GRANT CREATE, DROP ON DATABASE <db-name> TO <role-name>;`

Alternately you can run the `setup.sql` script that is slightly more sophisticated with `psql -U postgres -f .\setup.sql`