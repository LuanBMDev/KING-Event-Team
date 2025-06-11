CREATE DATABASE KING_Event_Team;

USE KING_Event_Team;

CREATE TABLE Localizacao
(
    codLocal INT PRIMARY KEY AUTO_INCREMENT,
    nomeLocal VARCHAR(100) NOT NULL,
    CEP VARCHAR(9),
    endereco VARCHAR(255) NOT NULL,
    numeroLocal INT,
    cidade VARCHAR(100),
    estado VARCHAR(25),
    tipoLocal VARCHAR(10) NOT NULL
);

CREATE TABLE Categoria 
(
    codCat INT PRIMARY KEY AUTO_INCREMENT,
    nomeCat VARCHAR(32) NOT NULL
);

CREATE TABLE Evento
(
    codEvento INT PRIMARY KEY AUTO_INCREMENT,
    nomeEvento VARCHAR(100) NOT NULL,
    dataInicio VARCHAR(10) NOT NULL,
    dataFim VARCHAR(10) NOT NULL,
    statusEvento VARCHAR(12) NOT NULL,
    codLocal INT NOT NULL,
    codCat INT NOT NULL,
    precoIngresso NUMERIC NOT NULL,
    FOREIGN KEY (codLocal) REFERENCES Localizacao(codLocal),
    FOREIGN KEY (codCat) REFERENCES Categoria(codCat)
);

CREATE TABLE Pessoa
(
    CPF VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE Expositor 
(
    codExpo INT PRIMARY KEY AUTO_INCREMENT,
    nomeFant VARCHAR(100) NOT NULL,
    CPFCNPJ VARCHAR(20) NOT NULL,
    logoExpo VARCHAR(255),
    emailExpo VARCHAR(100) NOT NULL,
    telefoneExpo VARCHAR(20) NOT NULL
);

CREATE TABLE Exposicao 
(
    codEvento INT,
    codExpo INT,
    descricao VARCHAR(255) NOT NULL,
    PRIMARY KEY (codEvento, codExpo),
    FOREIGN KEY (codEvento) REFERENCES Evento(codEvento),
    FOREIGN KEY (codExpo) REFERENCES Expositor(codExpo)
);

CREATE TABLE Ingresso 
(
    codEvento INT,
    codVisitante VARCHAR(20),
    totalPago NUMERIC NOT NULL,
    meiaEntrada INT NOT NULL,
    PRIMARY KEY (codEvento, codVisitante),
    FOREIGN KEY (codEvento) REFERENCES Evento(codEvento),
    FOREIGN KEY (codVisitante) REFERENCES Pessoa(CPF)
);
