-- Creation of product table
CREATE TABLE IF NOT EXISTS todo (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title varchar(250) NOT NULL,
  note varchar(250) NOT NULL,
  owner varchar(250) NOT NULL,
  PRIMARY KEY (id)
);