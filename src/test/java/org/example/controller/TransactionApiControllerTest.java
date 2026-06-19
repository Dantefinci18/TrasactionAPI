package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TransactionIdsResponse;
import org.example.dto.TransactionRequest;
import org.example.dto.TransactionSumResponse;
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
        TransactionRequest request = new TransactionRequest(5000,"cars",null);

        this.mockMvc.perform(put("/transactions/10").contentType(MediaType.APPLICATION_JSON)
                            .content(this.objectMapper.writeValueAsString(request))).andExpect(status().isOk());

        when(this.transactionService.getTransactionIdsType("cars")).thenReturn(new ArrayList<>(List.of(10L)));
        String responseBody = this.mockMvc.perform(get("/transactions/types/cars")).andReturn().getResponse().getContentAsString();
        TransactionIdsResponse response = this.objectMapper.readValue(responseBody, TransactionIdsResponse.class);
        assertEquals(List.of(10L), response.transactionIds());
    }

    @Test
    @Disabled
    public void seObtieneLaSumaDeLasTransaccionesConectadasPorSuPrentIdATransaccionId() throws Exception {
        TransactionRequest firstRequest = new TransactionRequest(5000,"cars",null);
        TransactionRequest secondRequest = new TransactionRequest(10000,"shopping",10L);
        TransactionRequest thirdRequest = new TransactionRequest(5000, "shopping",11L);

        this.mockMvc.perform(put("/transactions/10").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(firstRequest))).andExpect(status().isOk());
        this.mockMvc.perform(put("/transactions/11").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(secondRequest))).andExpect(status().isOk());
        this.mockMvc.perform(put("/transactions/12").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(thirdRequest))).andExpect(status().isOk());

        String responseBody = mockMvc.perform(get("/transactions/sum/10")).andReturn().getResponse().getContentAsString();
        TransactionSumResponse response = this.objectMapper.readValue(responseBody, TransactionSumResponse.class);
        assertEquals(2000,response.sum());

    }
}
