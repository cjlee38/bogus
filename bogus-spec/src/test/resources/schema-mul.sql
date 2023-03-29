create table mul_uni
(
    id bigint primary key auto_increment,
    a  tinyint,
    b  tinyint
);
alter table mul_uni
    add unique (a, b);

create table mul_pri
(
    a_id tinyint,
    b_id tinyint,
    primary key (a_id, b_id)
);

