package br.com.fiap.ms_pagamento.tests;

import br.com.fiap.ms_pagamento.dto.PagamentoDTO;
import br.com.fiap.ms_pagamento.model.Pagamento;
import br.com.fiap.ms_pagamento.model.Status;

import java.math.BigDecimal;

// Classe para instanciar objetos
public class Factory {

    public static Pagamento createPagamento(){

        Pagamento pagamento = new Pagamento(1L, BigDecimal.valueOf(32.25),
                "Bach", "2365145936541245", "07/28","585",
                Status.CRIADO, 1L, 2L);
        return pagamento;
    }

    public static PagamentoDTO createPagamentoDTO(){
        Pagamento pagamento = createPagamento();
        return new PagamentoDTO(pagamento);
    }

    public static PagamentoDTO createNewPagamentoDTO(){
        Pagamento pagamento = createPagamento();
        pagamento.setId(null);
        return new PagamentoDTO(pagamento);
    }
}
