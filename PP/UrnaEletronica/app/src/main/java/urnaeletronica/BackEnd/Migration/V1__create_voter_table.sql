CREATE TABLE  IF NOT EXISTS Voter (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    etitulo VARCHAR(12) NOT NULL,
    type VARCHAR(255) NOT NULL
);

CREATE INDEX etitulo_index ON Voter (etitulo);

--  create voter 12345
INSERT INTO Voter (name, cpf, etitulo, type) VALUES ('admin', '12345', '12345', 'admin');
