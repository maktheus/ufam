create table VotoCandidato (
    id integer primary key autoincrement,
    etitulo varchar(12) not null,
    -- foreign key (candidateNumber) references Candidate(candidateNumber),
    candidateNumber integer not null,
    created_at datetime not null,
    updated_at datetime not null,
    foreign key (candidateNumber) references Candidate(candidateNumber)
);