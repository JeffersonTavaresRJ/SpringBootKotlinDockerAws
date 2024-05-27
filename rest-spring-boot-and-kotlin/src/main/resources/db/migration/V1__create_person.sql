CREATE TABLE persons (
  id bigint NOT NULL AUTO_INCREMENT,
  address varchar(100) NOT NULL,
  first_name varchar(255) NOT NULL,
  gender varchar(6) NOT NULL,
  last_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)