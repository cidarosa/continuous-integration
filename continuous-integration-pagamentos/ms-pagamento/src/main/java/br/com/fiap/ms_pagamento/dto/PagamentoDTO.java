package br.com.fiap.ms_pagamento.dto;

import br.com.fiap.ms_pagamento.model.Pagamento;
import br.com.fiap.ms_pagamento.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoDTO {

    private Long id;

    @NotNull(message = "Campo obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private BigDecimal valor;
    @Size(max = 100, message = "Máximo de 100 caracteres")
    private String nome; // Nome no cartão
    @Size(max = 19, message = "Número do cartão deve ter no máximo 19 caracteres")
    private String numeroDoCartao; // XXXX XXXX XXXX XXXX
    @Size(min = 5, max = 5, message = "A validade do cartão dever ter 5 caracteres")
    private String validade; // validade do cartão - MM/AA
    @Size(min = 3, max = 3, message = "O código de segurança do cartão deve ter 3 caracteres")
    private String codigoDeSeguranca; // código de segurança - XXX

    @Enumerated(value = EnumType.STRING)
    private Status status;  // Status do pagamento
    @NotNull(message = "Pedido ID é obrigatório")
    @Positive(message = "O ID do pedido deve ser um número positivo")
    private Long pedidoId;  // Id do pedido
    @NotNull(message = "Forma de pagamento ID é obrigatório")
    @Positive(message = "O ID da forma de pagamento deve ser um número positivo")
    private Long formaDePagamentoId; // 1 - dinheiro | 2 - cartão | 3 - pix

    public PagamentoDTO(Pagamento entity) {
        id = entity.getId();
        valor = entity.getValor();
        nome = entity.getNome();
        numeroDoCartao = entity.getNumeroDoCartao();
        validade = entity.getValidade();
        codigoDeSeguranca = entity.getCodigoDeSeguranca();
        status = entity.getStatus();
        pedidoId = entity.getPedidoId();
        formaDePagamentoId = entity.getFormaDePagamentoId();
    }
}
