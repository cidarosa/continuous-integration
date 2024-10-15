package br.com.fiap.ms_pagamento.repository;

import br.com.fiap.ms_pagamento.model.Pagamento;
import br.com.fiap.ms_pagamento.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class PagamentoRepositoryTests {

    @Autowired
    private PagamentoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalPagamento;

    //Vai ser executado antes de cada teste
    @BeforeEach
    void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        //verificar quanto registro tem no seed
        countTotalPagamento = 6L;
    }

    @Test
    @DisplayName("Deveria excluir pagamento quando Id existe")
    public void deleteShouldDeletObjectWhenIdExists() {
        // Act
        repository.deleteById(existingId);
        // Assert
        Optional<Pagamento> result = repository.findById(existingId);
        //testa se existe um obj dentro do Optional
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("save Deveria salvar objeto com auto incremento quando Id é nulo")
    public void saveShouldPersistWithAutIncrementWhenIdIsNull(){

        Pagamento pagamento = Factory.createPagamento();
        pagamento.setId(null);
        pagamento = repository.save(pagamento);
        Assertions.assertNotNull(pagamento.getId());
        // verifica se é o próximo ID
        Assertions.assertEquals(countTotalPagamento + 1, pagamento.getId());
    }

    @Test
    @DisplayName("findById Deveria retornar um Optional não vazio quando o Id existe")
    public void findByIdShoulReturnNonEmptyOptionalWhenExistsId(){

        Optional<Pagamento> result = repository.findById(existingId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("findById Deveria retornar um Optional vazio quando o Id não existe")
    public void findByIdShoulReturnEmptyOptionalWhenIdDoesNotExists(){

        Optional<Pagamento> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
        // ou
        Assertions.assertTrue(result.isEmpty());
    }

}

// mudança no deleteById na versão 3.X.X do spring boot - ele não lança exception
//esse teste falhou por causa da versão do Spring boot
//não vai ser implementado dessa forma
//    @Test
////    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
////        long nonExistingId = 100L;
////        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
////            repository.deleteById(nonExistingId);
////        });
////    }










