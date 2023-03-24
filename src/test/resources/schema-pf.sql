
use pf;

set foreign_key_checks = 0;

drop table if exists primary_sequence;
drop table if exists foreign_ref_primary_sequence;
drop table if exists foreign_nullable_ref_primary_sequence;
drop table if exists foreign_primary_ref_primary_sequence;

drop table if exists primary_auto_increment;
drop table if exists foreign_ref_primary_auto_increment;
drop table if exists foreign_nullable_ref_primary_auto_increment;
drop table if exists foreign_primary_ref_primary_auto_increment;

drop table if exists primary_uuid;
drop table if exists foreign_ref_primary_uuid;
drop table if exists foreign_nullable_ref_primary_uuid;
drop table if exists foreign_primary_ref_primary_uuid;

set foreign_key_checks = 1;

create table primary_sequence
(
    id bigint primary key
);

create table foreign_ref_primary_sequence
(
    id bigint primary key auto_increment,
    ref_id bigint not null,
    foreign key (ref_id) references primary_sequence (id)
);

create table foreign_nullable_ref_primary_sequence
(
    id bigint primary key auto_increment,
    ref_id bigint,
    foreign key (ref_id) references primary_sequence (id)
);

create table foreign_primary_ref_primary_sequence
(
    ref_id bigint primary key,
    foreign key (ref_id) references primary_sequence (id)
);





create table primary_auto_increment
(
    id bigint primary key auto_increment
);

create table foreign_ref_primary_auto_increment
(
    id bigint primary key auto_increment,
    ref_id bigint not null,
    foreign key (ref_id) references primary_auto_increment (id)
);

create table foreign_nullable_ref_primary_auto_increment
(
    id bigint primary key auto_increment,
    ref_id bigint,
    foreign key (ref_id) references primary_auto_increment (id)
);

create table foreign_primary_ref_primary_auto_increment
(
    ref_id bigint primary key auto_increment,
    foreign key (ref_id) references primary_auto_increment (id)
);




create table primary_uuid
(
    uuid varchar(255) primary key,
    name varchar(255) not null
);

create table foreign_ref_primary_uuid
(
    id bigint primary key auto_increment,
    ref_uuid varchar(255) not null,
    foreign key (ref_uuid) references primary_uuid (uuid)
);

create table foreign_nullable_ref_primary_uuid
(
    id bigint primary key auto_increment,
    ref_uuid varchar(255) not null,
    foreign key (ref_uuid) references primary_uuid (uuid)
);

create table foreign_primary_ref_primary_uuid
(
    ref_uuid varchar(255) primary key,
    foreign key (ref_uuid) references primary_uuid (uuid)
);


