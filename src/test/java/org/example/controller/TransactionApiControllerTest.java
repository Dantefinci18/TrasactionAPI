package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TransactionIdsResponse;
import org.example.dto.TransactionRequest;
import org.example.dto.TransactionSumResponse;
import org.example.model.ParentIdError;
import org.example.model.TransactionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionApiController.class)
public class TransactionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TransactionService transactionService;
    @Test
    public void realizarUnaTransaccionRespondeOK() throws Exception {
        TransactionRequest request = new TransactionRequest(5000,"cars",null);

        mockMvc.perform(put("/transactions/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void seObtieneElIdAlBuscarPorTipoAutos() throws Exception{
        when(this.transactionService.getTransactionIdsType("cars")).thenReturn(new ArrayList<>(List.of(10L)));
        String responseBody = this.mockMvc.perform(get("/transactions/types/cars")).andReturn().getResponse().getContentAsString();
        TransactionIdsResponse response = this.objectMapper.readValue(responseBody, TransactionIdsResponse.class);
        assertEquals(List.of(10L), response.transactionIds());
    }

    @Test
    public void seObtieneLaSumaDeLasTransaccionesConectadasPorSuPrentIdATransaccionId() throws Exception {
        when(this.transactionService.getTransactionSum(10)).thenReturn(20000d);
        String responseBody = mockMvc.perform(get("/transactions/sum/10")).andReturn().getResponse().getContentAsString();
        TransactionSumResponse response = this.objectMapper.readValue(responseBody, TransactionSumResponse.class);
        assertEquals(20000,response.sum());
    }

    @Test
    public void seObtieneLaSumaDeLasTransacionesDeLasSubRamas() throws Exception{
        when(this.transactionService.getTransactionSum(11)).thenReturn(15000d);
        String responseBody = mockMvc.perform(get("/transactions/sum/11")).andReturn().getResponse().getContentAsString();
        TransactionSumResponse response = this.objectMapper.readValue(responseBody, TransactionSumResponse.class);
        assertEquals(15000,response.sum());
    }

    @Test
    @Disabled
    public void seLanzaErrorSiSeRegistraUnaTransaccionConParentIdQueNoTieneUnaTransaccion() throws Exception {
        TransactionRequest request = new TransactionRequest(5000,"cars",2L);
        doThrow(new ParentIdError()).when(transactionService).create(10, 5000, "cars", 2L);
        mockMvc.perform(put("/transactions/10").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
