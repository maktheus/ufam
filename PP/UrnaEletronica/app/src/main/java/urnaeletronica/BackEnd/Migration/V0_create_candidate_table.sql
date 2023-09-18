create table Candidate (
    id integer primary key autoincrement,
    -- foreign key (etitulo) references Voter(etitulo),
    etitulo varchar(12) not null,
    candidateNumber integer not null,
    created_at datetime not null,
    updated_at datetime not null,
    foreign key (etitulo) references Voter(etitulo)
);