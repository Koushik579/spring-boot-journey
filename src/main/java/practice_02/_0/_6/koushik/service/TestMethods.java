package practice_02._0._6.koushik.service;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import practice_02._0._6.koushik.entity.AuditEntity;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.repository.AuditRepository;
import practice_02._0._6.koushik.repository.UserRepository;

@Service
public class TestMethods {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public TestMethods(AuditRepository auditRepository, UserRepository userRepository, AuditService auditService) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

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

    @Transactional(propagation = Propagation.NESTED)
    public void testNested(long id, String name){
        UserEntity entity = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No User Found with id: "+id));
        AuditEntity auditEntity = new AuditEntity();
        auditService.saveLog(auditEntity, entity,
                "Name Update Attempt : " + name);
    }

    @PostConstruct
    public String testPostConstruct(){
        return "PostConstruct Executed";
    }

    private int count = 0;
    private String testResult = "Not yest started";
    @Scheduled(fixedRate = 6000)
    public String testScheduled(){
        count++;
        testResult = "Test Schedule :" + count;
        System.out.println(testResult);
        return testResult;
    }

    public void getTestResult(){
        System.out.println(testResult);
    }
}
