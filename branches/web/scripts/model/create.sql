
CREATE TABLE user_roles (
       rolename             VARCHAR(100) NOT NULL,
       username             VARCHAR(100) NULL
);


CREATE TABLE users (
       username             VARCHAR(100) NOT NULL,
       passwd               VARCHAR(100) NOT NULL
);


CREATE TABLE bug_report (
       message              VARCHAR(1000) NOT NULL,
       userName             VARCHAR(40) NULL,
       email                VARCHAR(40) NULL,
       status               VARCHAR(40) NULL,
       response             VARCHAR(1000) NULL,
       date                 DATE NULL,
       time                 DATE NULL
);

