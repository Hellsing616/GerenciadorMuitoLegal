create table lancamento(
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
descricao varchar(50) ,
dt_vencimento date ,
dt_pagamento date ,
valor decimal (10,2) ,
observacao varchar(200),
tipo varchar(20) ,
codigo_categoria bigint(20) ,
codigo_pessoa bigint(20) ,
foreign key(codigo_categoria) references categoria(codigo),
foreign key(codigo_pessoa) references pessoa(codigo)
)engine=InnoDB charset=utf8;
