package org.example.model;
import org.example.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.Optional;

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
        Optional<Transaction> transactionOptional = Optional.of(new Transaction(10, 5000, "cars",null));
        when(transactionRepository.findById(10L)).thenReturn(transactionOptional);
        when(transactionRepository.findByParentId(10L))
                .thenReturn(List.of(new Transaction(11, 10000, "shopping",null)));
        when(transactionRepository.findByParentId(11L))
                .thenReturn(List.of(new Transaction(12, 5000, "shopping",null)));
        double suma = this.transactionService.getTransactionSum(10);
        assertEquals(20000,suma);
    }

    @Test
    public void seObtieneLaSumaDeLasTransaccionesDeLasSubRamas(){
        Optional<Transaction> transactionOptional = Optional.of(new Transaction(11, 10000, "shopping",null));
        when(transactionRepository.findById(11L)).thenReturn(transactionOptional);
        when(transactionRepository.findByParentId(11L))
                .thenReturn(List.of(new Transaction(12, 5000, "shopping",null)));

        double suma = this.transactionService.getTransactionSum(11);
        assertEquals(15000,suma);
    }

    @Test
    public void seObieneLaSumaDeDosSubtransaciones(){
        Optional<Transaction> transactionOptional = Optional.of(new Transaction(10, 5000, "cars", null));
        when(transactionRepository.findById(10L)).thenReturn(transactionOptional);

        when(transactionRepository.findByParentId(10L))
                .thenReturn(List.of(
                        new Transaction(11, 10000, "shopping", null),
                        new Transaction(13, 5000, "shopping", null)
                ));

        when(transactionRepository.findByParentId(11L))
                .thenReturn(List.of(
                        new Transaction(12, 15000, "shopping", null),
                        new Transaction(14, 5000, "shopping", null)
                ));

        when(transactionRepository.findByParentId(13L))
                .thenReturn(List.of(new Transaction(15, 5000, "shopping", null)));

        double suma = this.transactionService.getTransactionSum(10);
        assertEquals(45000, suma);
    }

    @Test
    public void seLanzaErrorSiSeCreaUnParentIdQueNoTieneTranactionId(){
        when(this.transactionRepository.findById(13L)).thenReturn(Optional.empty());

            assertThrows(ParentIdError.class, () -> {
                this.transactionService.create(11L, 10000, "cars", 13L);
            });
    }
}
