CREATE TABLE IF NOT EXISTS tb_pagamento (
    id bigint NOT NULL auto_increment,
    valor decimal(38,2) NOT NULL,
    nome varchar(255),
    numero_do_cartao varchar(255),
    validade varchar(255),
    codigo_de_seguranca varchar(255),
    status enum ('CANCELADO','CONFIRMADO','CRIADO') NOT NULL,
    pedido_id bigint NOT NULL,
    forma_de_pagamento_id bigint NOT NULL,

    primary key (id)
);