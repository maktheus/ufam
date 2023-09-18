create table Voter (
    id integer primary key autoincrement,
    name varchar(255) not null,
    cpf varchar(11) not null,
    etitulo varchar(12) not null,
    created_at datetime not null,
    updated_at datetime not null
);
