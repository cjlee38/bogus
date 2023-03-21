drop table if exists e;
drop table if exists d;
drop table if exists c;
drop table if exists b;
drop table if exists a;

/*
expected
1. a <- b <- d <- e <- c
2. a <- c <- b <- d <- e
 */
create table a
(
    id bigint primary key auto_increment
);

create table b
(
    id   bigint primary key auto_increment,
    a_id bigint not null,
    foreign key (a_id) references a (id)
);

create table c
(
    id   bigint primary key auto_increment,
    a_id bigint,
    foreign key (a_id) references a (id)
);

create table d
(
    id   bigint primary key auto_increment,
    b_id bigint not null,
    foreign key (b_id) references b (id)
);

create table e
(
    id   bigint primary key auto_increment,
    b_id bigint not null,
    d_id bigint not null,
    foreign key (b_id) references b (id),
    foreign key (d_id) references d (id)
);
