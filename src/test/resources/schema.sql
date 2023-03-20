create table if not exists team
(
    id       bigint primary key auto_increment,
    name     varchar(255) not null,
    sub_name varchar(30)
);

create table if not exists member
(
    id   bigint primary key auto_increment,
    name varchar(255) not null,
    age  mediumint    not null
);
