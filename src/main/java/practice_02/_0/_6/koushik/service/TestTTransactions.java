package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import practice_02._0._6.koushik.repository.AuditRepository;
import practice_02._0._6.koushik.repository.UserRepository;

@Service
public class TestTTransactions {
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;
    private final AuditService auditService;
    private final UserService userService;
    private final TestMethods testMethods;

    public TestTTransactions(UserRepository userRepository, AuditRepository auditRepository, AuditService auditService, UserService userService, TestMethods testMethods) {
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
        this.auditService = auditService;
        this.userService = userService;
        this.testMethods = testMethods;
    }

    //Temporary test method Transaction tests
    public void testSupportsWithoutTransaction() {
        auditService.getLogsByUser(1L);
    }

    public String testingMandatory(){
        return testMethods.Mandatorytester();
    }

}
