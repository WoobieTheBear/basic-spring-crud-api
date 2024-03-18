
-- <START DATABASE CREATION AND SCHEMA SETUP>
DROP DATABASE IF EXISTS tutorial_db;
CREATE DATABASE tutorial_db;
\connect tutorial_db;

-- set up the role for jdbc called "tutorial_user"
DROP ROLE IF EXISTS tutorial_user;
-- [INFO]: this user and password is referenced in ./gravel/src/main/resources/application.properties
CREATE ROLE tutorial_user WITH PASSWORD 'th3-P455word+f0r-Connection';
ALTER ROLE tutorial_user WITH LOGIN;
GRANT CREATE, CONNECT, TEMPORARY ON DATABASE tutorial_db TO tutorial_user;

-- set up a schema for the data
DROP SCHEMA IF EXISTS black_data;
CREATE SCHEMA black_data;
SET search_path TO black_data;
GRANT CREATE, USAGE ON SCHEMA black_data TO tutorial_user;



--
-- Setup tables for app `crud`
--

-- <START DROP ALL PREVIOUS DATA>
DROP TABLE IF EXISTS secret_identity;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS workcontract;



-- <START ONE-TO-ONE SECRET_IDENTITY SETUP>

CREATE TABLE secret_identity (
  id BIGSERIAL PRIMARY KEY,
  secret_name VARCHAR (63) NOT NULL
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON secret_identity TO tutorial_user;

-- Configure the primary key sequence for person_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE secret_identity_id_seq TO tutorial_user;

-- Following line makes the person_id_seq start at 20000
ALTER SEQUENCE IF EXISTS secret_identity_id_seq RESTART WITH 20000;



-- <START PERSON SETUP>

CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR (63) NOT NULL,
  last_name VARCHAR (63) NOT NULL,
  email VARCHAR (255) UNIQUE NOT NULL,
  created_at TIMESTAMP NOT NULL,
  secret_identity_id BIGINT,
  CONSTRAINT secret_identity_fk
    FOREIGN KEY(secret_identity_id)
      REFERENCES secret_identity(id)
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON person TO tutorial_user;

-- Configure the primary key sequence for person_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE person_id_seq TO tutorial_user;

-- Following line makes the person_id_seq start at 10000
ALTER SEQUENCE IF EXISTS person_id_seq RESTART WITH 10000;



-- <START ONE-TO-MANY PET SETUP>

CREATE TABLE pet (
  id BIGSERIAL PRIMARY KEY,
  pet_name VARCHAR (255) NOT NULL,
  species VARCHAR (255) NOT NULL,
  person_id BIGINT,
  CONSTRAINT person_fk
    FOREIGN KEY(person_id)
      REFERENCES person(id)
      ON UPDATE NO ACTION
      ON DELETE NO ACTION
);

-- Grant necessary permissions for JDBC to access table
GRANT INSERT, UPDATE, SELECT, DELETE, TRUNCATE ON pet TO tutorial_user;

-- Configure the primary key sequence for person_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE pet_id_seq TO tutorial_user;

-- Following line makes the person_id_seq start at 30000
ALTER SEQUENCE IF EXISTS pet_id_seq RESTART WITH 30000;



-- <START COMPANY SETUP>

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

CREATE TABLE workcontract (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR (255) UNIQUE NOT NULL,
  contract_workload INT NOT NULL,
  salary INT NOT NULL,
  company_id BIGINT NOT NULL,
  person_id BIGINT NOT NULL,
  CONSTRAINT fk_person
    FOREIGN KEY(person_id) 
      REFERENCES person(id),
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

