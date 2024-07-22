create table expenses(
    id bigint not null auto_increment,
    description varchar(300) not null,
    value decimal(10,2) not null,
    date DATE not null,
 primary key(id)
);