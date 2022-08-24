DROP TABLE IF EXISTS todo;

CREATE TABLE todo (
  id                      BIGINT NOT NULL AUTO_INCREMENT,
  title             VARCHAR(250) NOT NULL,
  note              VARCHAR(500),
  owner             VARCHAR(250),
  PRIMARY KEY(id)
);

INSERT INTO todo (title, note, owner) VALUES ('Send email to customet test', 'Send email to customer about issues', 'Juan');
INSERT INTO todo (title, note, owner) VALUES ('Scheduler meeting to review the JVM issues test', 'Review JVM arg issues', 'Jose');
INSERT INTO todo (title, note, owner) VALUES ('Implement the new Service test', 'Implement the new service for cars', 'Eduardo');

