CREATE TABLE  IF NOT EXISTS VotoCandidato (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    etitulo VARCHAR(12) NOT NULL,
    candidateNumber INTEGER NOT NULL,
    FOREIGN KEY (candidateNumber) REFERENCES Candidate(candidateNumber)
);
