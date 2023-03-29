
create table if not exists numeric_types
(
    a bigint,
    b int,
    c mediumint,
    d smallint,
    e tinyint,
    f decimal(5, 5),
    g numeric(5, 2),
    h decimal(8),
    i float,
    j float4,
    k float8,
    l float(10),
    m float(30),
    n double
);

create table if not exists string_types
(
    a varchar(1),
    b varchar(255),
    c nvarchar(123),
    d varchar(1000),
    e char,
    f char(20),
    g character,
    h character(30),
    i nchar(125),
    j longtext
);
