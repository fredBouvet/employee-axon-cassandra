create keyspace employee with replication ={'class':'SimpleStrategy', 'replication_factor':1};

use employee;

CREATE TABLE employee_by_id
(
    id        uuid,
    name      text,
    address   text,
    email     text,
    birthDate date,
    PRIMARY KEY (id)
);


CREATE TABLE employee_by_name
(
    name      text,
    id        uuid,
    address   text,
    email     text,
    birthDate date,
    PRIMARY KEY (name, id)
);

CREATE TABLE employee_by_birth_date
(
    birthDate date,
    id        uuid,
    name      text,
    address   text,
    email     text,
    PRIMARY KEY (birthDate, id)
);