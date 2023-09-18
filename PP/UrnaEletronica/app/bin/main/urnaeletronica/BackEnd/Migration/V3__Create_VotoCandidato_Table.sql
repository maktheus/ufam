CREATE TABLE VotoCandidato (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    etitulo VARCHAR(12) NOT NULL,
    candidateNumber INTEGER NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (candidateNumber) REFERENCES Candidate(candidateNumber)
);
