package org.example.model;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Test
    @Disabled
    public void sePersisteElRegistroDeUnaTransaccion(){
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepository);
        verify(transactionRepository).save(new Transaction(5000,"cars"));
        transactionService.create(10,5000,"cars");
    }
}
