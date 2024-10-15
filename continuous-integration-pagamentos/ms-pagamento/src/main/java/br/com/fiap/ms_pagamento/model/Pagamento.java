package br.com.fiap.ms_pagamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tb_pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal valor;
    private String nome; // Nome no cartão
    private String numeroDoCartao; // XXXX XXXX XXXX XXXX
    private String validade; // validade do cartão - MM/AA
    private String codigoDeSeguranca; // código de segurança - XXX
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;  // Status do pagamento
    @Column(nullable = false)
    private Long pedidoId;  // Id do pedido
    @Column(nullable = false)
    private Long formaDePagamentoId; // 1 - dinheiro | 2 - cartão | 3 - pix
}
