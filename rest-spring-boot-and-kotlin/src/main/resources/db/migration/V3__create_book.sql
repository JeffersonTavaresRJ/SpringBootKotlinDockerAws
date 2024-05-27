CREATE TABLE books (
  id bigint NOT NULL AUTO_INCREMENT,
  author longtext NOT NULL,
  launch_date datetime(6) DEFAULT NULL,
  price double NOT NULL,
  title longtext NOT NULL,
  PRIMARY KEY (id)
)