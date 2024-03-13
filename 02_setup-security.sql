\connect tutorial_db;



-- set up a schema for security
DROP SCHEMA IF EXISTS black_security;
CREATE SCHEMA black_security;
SET search_path TO black_security;
GRANT CREATE, USAGE ON SCHEMA black_security TO tutorial_user;



-- <START SECURITY SETUP>
DROP TABLE IF EXISTS auth_access_tuple;
DROP TABLE IF EXISTS auth_permission;
DROP TABLE IF EXISTS auth_entity;



-- create the table for users
-- this table name is referenced in src/main/java/ch/black/gravel/security/BlackSecurityConfig.java
CREATE TABLE auth_entity (
  id BIGSERIAL PRIMARY KEY,
  entity_email VARCHAR (127) NOT NULL,
  entity_name VARCHAR (127) NOT NULL,
  entity_key VARCHAR (255) NOT NULL,
  entity_group BIGINT,
  entity_active BOOLEAN NOT NULL
);

-- Grant necessary permissions for JDBC to access table
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON auth_entity TO tutorial_user;

-- Configure the primary key sequence for company_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE auth_entity_id_seq TO tutorial_user;



-- create the table for the permissions
-- this table name is referenced in src/main/java/ch/black/gravel/security/BlackSecurityConfig.java
CREATE TABLE auth_permission (
  id BIGSERIAL PRIMARY KEY,
  permission_name VARCHAR (63)
);

-- Grant necessary permissions for JDBC to access table
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON auth_permission TO tutorial_user;

-- Configure the primary key sequence for company_id_seq
GRANT SELECT, UPDATE, USAGE ON SEQUENCE auth_permission_id_seq TO tutorial_user;



-- create the table for the permission assignement
-- this table name is referenced in src/main/java/ch/black/gravel/security/BlackSecurityConfig.java
CREATE TABLE auth_access_tuple (
  entity_id BIGINT NOT NULL,
  CONSTRAINT fk_auth_entity
    FOREIGN KEY(entity_id)
      REFERENCES auth_entity(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  permission_id BIGINT NOT NULL,
  CONSTRAINT fk_auth_permission
    FOREIGN KEY(permission_id)
      REFERENCES auth_permission(id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,

  PRIMARY KEY (entity_id, permission_id)  
);
CREATE UNIQUE INDEX index_auth_access_tuple ON auth_access_tuple (entity_id, permission_id);

-- Grant necessary permissions for JDBC to access table
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON auth_access_tuple TO tutorial_user;



-- insert some test users [TODO: delete if in production]
INSERT INTO auth_entity VALUES 
(1, 'john.doe@doit.com', 'john', '$2a$10$bw1RuI7qANEwCTMgOYHJMOFcL6cSBQnsoG3VhoD3XU6xj4bbliwoq', 1, TRUE),
(2, 'mary.jane@wantiwant.com', 'mary', '$2a$12$RIa9VN2983iBgw56ZhaDTuPuY/KkVcSlMEp0kcgq2v/T5KTZ/hety', 1, TRUE),
(3, 'susan.miller@wantiwant.com', 'susan', '$2a$12$.V8XWbFUfwNQZz/y7CkBwuEz0w6PoilXnQi2aKoqYNrQNSOvfeUZm', 1, TRUE);

INSERT INTO auth_permission VALUES 
(1, 'USER'),
(2, 'POWER'),
(3, 'ADMIN');

INSERT INTO auth_access_tuple VALUES 
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3);


-- Following line makes the company_id_seq start at 4
ALTER SEQUENCE IF EXISTS auth_entity_id_seq RESTART WITH 4;
