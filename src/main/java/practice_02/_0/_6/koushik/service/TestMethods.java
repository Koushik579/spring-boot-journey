package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestMethods {

    @Transactional(propagation = Propagation.MANDATORY)
    public String Mandatorytester(){
        return "Joined the EXISTING TRANSACTION";
    }

}
