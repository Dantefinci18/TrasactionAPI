package org.example.model;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceTest {
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final TransactionService transactionService = new TransactionService(transactionRepository);

    @Test
    public void sePersisteElRegistroDeUnaTransaccion(){
        this.transactionService.create(10L,5000,"cars",null);
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(this.transactionRepository).save(transactionCaptor.capture());
        Transaction transaction = transactionCaptor.getValue();
        assertEquals("cars",transaction.getType());
        assertEquals(5000,transaction.getAmount());
        assertEquals(10,transaction.getId());
    }

    @Test
    public void seObtieneElPadreDelTipoDeTransaccion(){
        when(transactionRepository.findByType("cars"))
                .thenReturn(List.of(new Transaction(10, 5000, "cars",null)));

        ArrayList<Long> resultIds = this.transactionService.getTransactionIdsType("cars");
        assertEquals(List.of(10L),resultIds);
    }

    @Test
    public void seObtieneLaSumaDeTransacionesConectadasEntreParentIdYTransaccionId(){
        double suma = this.transactionService.getTransactionSum(10);
        assertEquals(2000,suma);
    }
}
