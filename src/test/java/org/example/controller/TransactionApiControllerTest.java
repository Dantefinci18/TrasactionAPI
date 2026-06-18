package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TransactionIdsResponse;
import org.example.dto.TransactionRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionApiController.class)
public class TransactionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void realizarUnaTransaccionRespondeOK() throws Exception {
        TransactionRequest request = new TransactionRequest(5000,"cars",null);

        mockMvc.perform(put("/transactions/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    public void seRealizaUnaTransaccionDeAutosYSeObtieneElIdAlBuscarPorTipoAutos() throws Exception{
        TransactionRequest firstRequest = new TransactionRequest(5000,"cars",null);
        TransactionRequest secondRequest = new TransactionRequest(10000,"shopping", 10);
        TransactionRequest thirdRequest = new TransactionRequest(5000,"shopping", 11);

        for(TransactionRequest request : List.of(firstRequest,secondRequest,thirdRequest) ) {
            mockMvc.perform(put("/transactions/10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        String responseBody = mockMvc.perform(get("/transactions/types/cars")).andReturn().getResponse().getContentAsString();
        TransactionIdsResponse response = objectMapper.readValue(responseBody, TransactionIdsResponse.class);
        assertEquals(List.of(10), response.transactionIds());
    }
}
