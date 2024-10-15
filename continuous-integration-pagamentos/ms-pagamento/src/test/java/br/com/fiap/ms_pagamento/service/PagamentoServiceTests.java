package br.com.fiap.ms_pagamento.service;

import br.com.fiap.ms_pagamento.dto.PagamentoDTO;
import br.com.fiap.ms_pagamento.model.Pagamento;
import br.com.fiap.ms_pagamento.repository.PagamentoRepository;
import br.com.fiap.ms_pagamento.service.exception.ResourceNotFoundException;
import br.com.fiap.ms_pagamento.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class PagamentoServiceTests {

    // Mock - Injetando
    @InjectMocks
    private PagamentoService service;

    // Mock do repositório
    @Mock
    private PagamentoRepository repository;

    //preparando os dados
    private Long existingId;
    private Long nonExistingId;

    //próximos testes
    private Pagamento pagamento;
    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    void setup() throws Exception {
        existingId = 1L;
        nonExistingId = 10L;
        //precisa simular o comportamento do objeto mockado
        //delete - quando id existe
        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        //delete - quando id não existe
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        //delete - primeiro caso - deleta
        // não faça nada (void) quando ...
        Mockito.doNothing().when(repository).deleteById(existingId);
        //próximos testes
        pagamento = Factory.createPagamento();
        pagamentoDTO = new PagamentoDTO(pagamento);
        //simulação do comportamento
        // findById
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(pagamento));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        // insert
        Mockito.when(repository.save(any())).thenReturn(pagamento);
        //update -primeiro caso - id existe
        Mockito.when(repository.getReferenceById(existingId)).thenReturn(pagamento);
        //update -segundo caso - id não existe
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("delete Deveria não fazer nada quando Id existe")
    public void deleteShouldDoNothingWhenIdExists() {
        // no service, delete é do tipo void
        Assertions.assertDoesNotThrow(
                () -> {
                    service.delete(existingId);
                }
        );
    }

    @Test
    @DisplayName("delete Deveria lançar exceção ResourceNotFoundException quando Id não existe")
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void findByIdShouldReturnPagamentoDTOWhenIdExists(){
        PagamentoDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getValor(), pagamento.getValor());
    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnPagamentoDTO(){

        PagamentoDTO result = service.insert(pagamentoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), pagamento.getId());
    }

    @Test
    public void updateShouldReturnPagamentoDTOWhenIdExists(){

        PagamentoDTO result = service.update(pagamento.getId(), pagamentoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getValor(), pagamento.getValor());
    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, pagamentoDTO);
        });
    }
}











