DROP TABLE IF EXISTS TODO;

CREATE TABLE TODO (
  id                      BIGINT NOT NULL AUTO_INCREMENT,
  title             VARCHAR(250) NOT NULL,
  note              VARCHAR(500),
  owner             VARCHAR(250),
  PRIMARY KEY(id)
);