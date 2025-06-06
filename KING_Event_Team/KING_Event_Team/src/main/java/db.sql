CREATE DATABASE KING_Event_Team;

USE KING_Event_Team;

CREATE TABLE Localizacoes 
(
    codLocal INT PRIMARY KEY AUTO_INCREMENT,
    nomeLocal VARCHAR(100),
    CEPLocal VARCHAR(10),
    enderecoLocal VARCHAR(100),
    numeroLocal INT,
    cidadeLocal VARCHAR(100),
    tipoLocal VARCHAR(100)
);

CREATE TABLE Categorias 
(
    codCat INT PRIMARY KEY AUTO_INCREMENT,
    nomeCat VARCHAR(100)
);

CREATE TABLE Eventos 
(
    codEvento INT PRIMARY KEY AUTO_INCREMENT,
    nomeEvento VARCHAR(100),
    descEvento VARCHAR(255),
    dataInicioEvento VARCHAR(10),
    dataFimEvento VARCHAR(10),
    statusEvento VARCHAR(100),
    codLocal INT,
    codCat INT,
    precoPadraoIngresso NUMERIC,
    FOREIGN KEY (codLocal) REFERENCES Localizacoes(codLocal),
    FOREIGN KEY (codCat) REFERENCES Categorias(codCat)
);

CREATE TABLE Pessoas 
(
    cpf VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE Expositores 
(
    codExpo INT PRIMARY KEY AUTO_INCREMENT,
    nomeFant VARCHAR(100),
    CPFCNPJ VARCHAR(20),
    logoExpo VARCHAR(255),
    emailExpo VARCHAR(100),
    telefoneExpo VARCHAR(20)
);

CREATE TABLE Exposicao 
(
    codEvento INT,
    codExpo INT,
    descricao VARCHAR(255),
    PRIMARY KEY (codEvento, codExpo),
    FOREIGN KEY (codEvento) REFERENCES Eventos(codEvento),
    FOREIGN KEY (codExpo) REFERENCES Expositores(codExpo)
);

CREATE TABLE Ingressos 
(
    codEvento INT,
    codVisitante INT,
    totalPago NUMERIC,
    meiaEntrada INT,
    PRIMARY KEY (codEvento, codVisitante),
    FOREIGN KEY (codEvento) REFERENCES Eventos(codEvento),
    FOREIGN KEY (codVisitante) REFERENCES Pessoas(cpf)
);
