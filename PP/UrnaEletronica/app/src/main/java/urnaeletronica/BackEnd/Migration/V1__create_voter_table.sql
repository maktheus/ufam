CREATE TABLE Voter (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    etitulo VARCHAR(12) NOT NULL
);

CREATE INDEX etitulo_index ON Voter (etitulo);
