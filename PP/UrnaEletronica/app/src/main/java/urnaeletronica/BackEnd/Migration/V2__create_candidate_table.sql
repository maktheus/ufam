CREATE TABLE Candidate (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    etitulo VARCHAR(12) NOT NULL,
    candidateNumber INTEGER NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (etitulo) REFERENCES Voter(etitulo)
);

CREATE INDEX candidateNumber_index ON Candidate (candidateNumber);
