CREATE TABLE contact (
  id        BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email     VARCHAR(255),
  telephone VARCHAR(255) NOT NULL,
  twitter   VARCHAR(255),
  skype     VARCHAR(255),
  photo     VARCHAR(255)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;