package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TestMethods {

    @Transactional(propagation = Propagation.MANDATORY)
    public String Mandatorytester(){
        return "Joined the EXISTING TRANSACTION";
    }

    @Transactional(propagation = Propagation.NEVER)
    public void testNever() {

        System.out.println("NEVER method executed");

        System.out.println(
                "Transaction active: " +
                        TransactionSynchronizationManager.isActualTransactionActive()
        );
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testNotSupported() {

        boolean active =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println("Inside NOT_SUPPORTED: " + active);
    }

}
