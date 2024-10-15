package br.com.fiap.ms_pagamento.controller;

import br.com.fiap.ms_pagamento.dto.PagamentoDTO;
import br.com.fiap.ms_pagamento.service.PagamentoService;
import br.com.fiap.ms_pagamento.service.exception.ResourceNotFoundException;
import br.com.fiap.ms_pagamento.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
public class PagamentoControllerTests {

    @Autowired
    private MockMvc mockMvc; //para chamar o endpoint
    //controller tem dependência do service
    //dependência mockada
    @MockBean
    private PagamentoService service;
    private PagamentoDTO pagamentoDTO;
    private Long existingId;
    private Long nonExistingId;
    //Converter para JSON o objeto Java e enviar na requisção
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() throws Exception {

        existingId = 1L;
        nonExistingId = 10L;

        //criando um PagamentoDTO
        pagamentoDTO = Factory.createPagamentoDTO();

        //Listando PagamentoDTO
        List<PagamentoDTO> list = List.of(pagamentoDTO);

        //simulando comportamento do service - findAll
        when(service.findAll()).thenReturn(list);

        //simulando comportamento do service - findById
        when(service.findById(existingId)).thenReturn(pagamentoDTO);
        // findById - id não existente - lança exception
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // simulando o comportamento do service - insert
        //any() simula o comportamento de qualquer objeto
        when(service.insert(any())).thenReturn(pagamentoDTO);

        // simulando o comportamento do service - update
        //any() simula o comportamento de qualquer objeto
        //quando usamos o any(), não pode usar objetos simples,
        // então precisamos do eq()
        when(service.update(eq(existingId), any())).thenReturn(pagamentoDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        // quando o retorno é Void
        // delete
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
    }

    @Test
    public void findAllShouldReturnListPagamentoDTO() throws Exception {

        //chamando uma requisição com o método get em /pagamentos
        ResultActions result = mockMvc.perform(get("/pagamentos")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnPagamentoDTOWhenIdExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/pagamentos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        //Assertions
        result.andExpect(status().isOk());
        //analisa se tem os campos em result
        // $ - acessar o objeto da resposta
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.valor").exists());
        result.andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("findById Deve retornar NotFound quando Id não existe - Erro 404")
    public void findByIdShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result = mockMvc.perform(get("/pagamentos/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        //Assertions
        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnProductDTOCreated() throws Exception {

        //POST tem corpo - JSON
        //Bean objectMapper para converter JAVA para JSON

        PagamentoDTO newDTO = Factory.createNewPagamentoDTO();
        String jsonBody = objectMapper.writeValueAsString(newDTO);

        mockMvc.perform(post("/pagamentos")
                        .content(jsonBody) //requisição
                        .contentType(MediaType.APPLICATION_JSON) //requisição
                        .accept(MediaType.APPLICATION_JSON)) //resposta
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    public void updateShouldReturnPagamentoDTOWhenIdExists() throws Exception {

        //PUT tem corpo - JSON
        //precisamos passar o corpo da requisição
        //Bean objectMapper para converter JAVA para JSON
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);

        mockMvc.perform(put("/pagamentos/{id}", existingId)
                        .content(jsonBody) //requisição
                        .contentType(MediaType.APPLICATION_JSON) //requisição
                        .accept(MediaType.APPLICATION_JSON)) //resposta
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.valor").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("Update Deve retornar NotFound quando Id não existe- Erro 404")
    public void updateShoulReturnNotFoundWhenIdDoesNotExist() throws Exception {

        //PUT tem corpo - JSON
        //precisamos passar o corpo da requisição
        //Bean objectMapper para converter JAVA para JSON
        String jsonBody = objectMapper.writeValueAsString(pagamentoDTO);

        mockMvc.perform(put("/pagamentos/{id}", nonExistingId)
                        .content(jsonBody) //requisição
                        .contentType(MediaType.APPLICATION_JSON) //requisição
                        .accept(MediaType.APPLICATION_JSON)) //resposta
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        mockMvc.perform(delete("/pagamentos/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("delete Deve retornar NotFound - Erro 404")
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        mockMvc.perform(delete("/pagamentos/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}


