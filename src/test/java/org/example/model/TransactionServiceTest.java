package org.example.model;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Test
    public void sePersisteElRegistroDeUnaTransaccion(){
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepository);
        transactionService.create(10,5000,"cars");
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(captor.capture());
        Transaction transaction = captor.getValue();
        assertEquals("cars",transaction.getType());
        assertEquals(5000,transaction.getAmount());
        assertEquals(10,transaction.getId());
    }
}
