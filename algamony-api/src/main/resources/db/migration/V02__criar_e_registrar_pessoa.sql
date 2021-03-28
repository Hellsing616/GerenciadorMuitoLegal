create table pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) ,
	ativo boolean,
	logradouro VARCHAR(100),
	numero VARCHAR(10),
	complemento VARCHAR(100),
	bairro VARCHAR(100),
	cep VARCHAR(10),
	cidade VARCHAR(100),
	estado VARCHAR(100)
) engine=InnoDB charset=utf8;

INSERT INTO pessoa (nome,ativo) values ('isaac',false);