DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  email                   VARCHAR(250) NOT NULL,
  first_name              VARCHAR(250) NOT NULL,
  last_name               VARCHAR(250) NOT NULL,
  PRIMARY KEY(email)
);

INSERT INTO customer (email, first_name, last_name) VALUES ('juan.aranda@globant.com', 'Juan', 'Aranda');
INSERT INTO customer (email, first_name, last_name) VALUES ('cata.tellez@globant.com', 'Cata', 'Tellez');

