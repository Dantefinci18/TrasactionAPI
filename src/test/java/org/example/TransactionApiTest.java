package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(TransactionApiController.class)
public class TransactionApiTest {
    @Test
    @Disabled
    public void realizarUnaTransaccionRespondeOK(){

    }

}
