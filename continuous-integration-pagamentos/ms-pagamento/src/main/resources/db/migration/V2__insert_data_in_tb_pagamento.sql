INSERT INTO tb_pagamento(valor, nome, numero_do_cartao, validade, codigo_de_seguranca, status, pedido_id, forma_de_pagamento_id) VALUES
(1200, 'Nicodemus C Souza', '1234567890123456', '12/30', '352', 'CRIADO', 1, 2),
(500.50, 'Amadeus Mozart',  '8597452369851245', '05/28', '647', 'CRIADO', 5, 2),
(1200, 'Maria Joaquina', '2457896547123654', '01/25', '543', 'CRIADO', 3, 2),
(1200, null, null, null, null, 'CRIADO', 4, 1),
(5500.25, null, null, null, null, 'CANCELADO', 4, 1),
(125.25, null, null, null, null, 'CONFIRMADO', 6, 1);

