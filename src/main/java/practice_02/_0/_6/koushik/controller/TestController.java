package practice_02._0._6.koushik.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice_02._0._6.koushik.service.AuditService;
import practice_02._0._6.koushik.service.TestTTransactions;
import practice_02._0._6.koushik.service.UserService;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final UserService userService;
    private final AuditService auditService;
    private final TestTTransactions testTTransactions;

    public TestController(UserService userService,
                          AuditService auditService,
                          TestTTransactions testTTransactions) {
        this.userService = userService;
        this.auditService = auditService;
        this.testTTransactions = testTTransactions;
    }

    @GetMapping("/log/transaction/supports")
    public String userUpdateLogsTestMethod(){
        testTTransactions.testSupportsWithoutTransaction();
        return "Check Console for the Transaction Status";
    }

    @GetMapping("/log/transaction/mandatory")
    public String transactionMandatoryTest(){
        return testTTransactions.testingMandatory();
    }
}
