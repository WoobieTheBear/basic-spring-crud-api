DROP DATABASE IF EXISTS tutorial_db;
CREATE DATABASE tutorial_db;
\connect tutorial_db;

DROP ROLE IF EXISTS tutorial_user;
-- [INFO]: this user and password is referenced in ./crud/src/main/resources/application.properties
CREATE ROLE tutorial_user WITH PASSWORD 'th3-P455word+f0r-Connection';
ALTER ROLE tutorial_user WITH LOGIN;
GRANT CREATE, CONNECT, TEMPORARY ON DATABASE tutorial_db TO tutorial_user;

--
-- Setup tables for app `crud`
--


-- <START PERSON SETUP>
DROP TABLE IF EXISTS person;

CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR (63) NOT NULL,
  last_name VARCHAR (63) NOT NULL,
  email VARCHAR (255) UNIQUE NOT NULL,
  created_at TIMESTAMP NOT NULL,
  last_login TIMESTAMP
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON person TO tutorial_user;

-- Configure the primary key sequence for person_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE person_id_seq TO tutorial_user;

-- Following line makes the person_id_seq start at 10000
ALTER SEQUENCE IF EXISTS person_id_seq RESTART WITH 10000;



-- <START COMPANY SETUP>
DROP TABLE IF EXISTS company;

CREATE TABLE company (
  id BIGSERIAL PRIMARY KEY,
  company_name VARCHAR (63) NOT NULL,
  contact_email VARCHAR (255) UNIQUE NOT NULL
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON company TO tutorial_user;

-- Configure the primary key sequence for company_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE company_id_seq TO tutorial_user;

-- Following line makes the company_id_seq start at 1000
ALTER SEQUENCE IF EXISTS company_id_seq RESTART WITH 1000;



-- <START CONTRACT SETUP>
DROP TABLE IF EXISTS workcontract;

CREATE TABLE workcontract (
  id BIGSERIAL PRIMARY KEY,
  contract_workload INT NOT NULL,
  salary INT NOT NULL,
  person_id BIGINT NOT NULL,
  CONSTRAINT fk_person
      FOREIGN KEY(person_id) 
        REFERENCES person(id),
  company_id BIGINT NOT NULL,
  CONSTRAINT fk_company
      FOREIGN KEY(company_id) 
        REFERENCES company(id)
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON workcontract TO tutorial_user;

-- Configure the primary key sequence for workcontract_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE workcontract_id_seq TO tutorial_user;

-- Following line makes the workcontract_id_seq start at 100000
ALTER SEQUENCE IF EXISTS workcontract_id_seq RESTART WITH 100000;
