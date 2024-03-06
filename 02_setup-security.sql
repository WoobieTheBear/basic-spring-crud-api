\connect tutorial_db;


-- <START SECURITY SETUP>
DROP TABLE IF EXISTS auth_permissions;
DROP TABLE IF EXISTS auth_entities;



-- create the table for users
-- this table name is referenced in src/main/java/ch/black/gravel/security/BlackSecurityConfig.java
CREATE TABLE auth_entities (
  entity_name VARCHAR (127) PRIMARY KEY,
  entity_key VARCHAR (255) NOT NULL,
  entity_group BIGINT NOT NULL,
  entity_active BOOLEAN NOT NULL
);

-- Grant necessary permissions for JDBC to access table
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON auth_entities TO tutorial_user;



-- create the table for the role assignement
-- this table name is referenced in src/main/java/ch/black/gravel/security/BlackSecurityConfig.java
CREATE TABLE auth_permissions (
  entity_name VARCHAR (127),
  CONSTRAINT fk_entity_permissions
    FOREIGN KEY(entity_name) 
      REFERENCES auth_entities(entity_name),
  permission_name VARCHAR (63),

  PRIMARY KEY (entity_name, permission_name)
);
CREATE UNIQUE INDEX index_entity_permissions ON auth_permissions (entity_name, permission_name);

-- Grant necessary permissions for JDBC to access table
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON auth_permissions TO tutorial_user;



-- insert some test users [TODO: delete if in production]
INSERT INTO auth_entities VALUES 
('john', '{bcrypt}$2a$12$clB2WSo2VsBynQEfNm9tBecZO1kmTP85DhO7vLod6Hhz16sZvYaAy', 1, TRUE),
('mary', '{bcrypt}$2a$12$RIa9VN2983iBgw56ZhaDTuPuY/KkVcSlMEp0kcgq2v/T5KTZ/hety', 1, TRUE),
('susan', '{bcrypt}$2a$12$.V8XWbFUfwNQZz/y7CkBwuEz0w6PoilXnQi2aKoqYNrQNSOvfeUZm', 1, TRUE);

INSERT INTO auth_permissions VALUES 
('john', 'USER'),
('mary', 'USER'),
('mary', 'POWER'),
('susan', 'USER'),
('susan', 'ADMIN'),
('susan', 'POWER');
