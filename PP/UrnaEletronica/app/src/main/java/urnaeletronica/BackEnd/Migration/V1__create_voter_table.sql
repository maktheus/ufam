-- Criação da tabela Voter
CREATE TABLE IF NOT EXISTS Voter (
    etitulo VARCHAR(12) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);


