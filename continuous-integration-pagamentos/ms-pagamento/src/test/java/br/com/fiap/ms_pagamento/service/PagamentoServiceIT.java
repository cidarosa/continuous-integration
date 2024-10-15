package br.com.fiap.ms_pagamento.service;

import br.com.fiap.ms_pagamento.repository.PagamentoRepository;
import br.com.fiap.ms_pagamento.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //carrega o contexto da aplicação
@Transactional  //rollback no DB
public class PagamentoServiceIT {

    @Autowired
    private PagamentoService service;
    @Autowired
    private PagamentoRepository repository;
    //preparando os dados
    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPagamento;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 50L;
        countTotalPagamento = 6L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {
        service.delete(existingId);
        Assertions.assertEquals(countTotalPagamento - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findAllShouldReturnListPagamentoDTO(){

        var result = service.findAll();
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(countTotalPagamento, result.size());
        Assertions.assertEquals(Double.valueOf(1200.00), result.get(0).getValor().doubleValue());
        Assertions.assertEquals("Nicodemus C Souza", result.get(0).getNome());
        Assertions.assertEquals("Amadeus Mozart", result.get(1).getNome());
        Assertions.assertNull(result.get(5).getNome());
        //ou Assertions.assertEquals(null, result.get(5).getNome());
    }

}
