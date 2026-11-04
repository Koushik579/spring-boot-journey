package practice_02._0._6.koushik.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.service.AuditService;
import practice_02._0._6.koushik.service.TestMethods;
import practice_02._0._6.koushik.service.TestTTransactions;
import practice_02._0._6.koushik.service.UserService;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestTTransactions testTTransactions;
    private final TestMethods testMethods;
    public TestController(TestTTransactions testTTransactions, TestMethods testMethods) {
        this.testTTransactions = testTTransactions;
        this.testMethods = testMethods;
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

    @GetMapping("/log/transaction/never")
    public String transactionNeverTest(){
        return testTTransactions.testingNever();
    }

    @GetMapping("/log/transaction/notsupported")
    public void transactionNotSupportedTest(){
        testTTransactions.testingNotSupported();
    }

    @PatchMapping("/log/transaction/nested/{id}")
    public ResponseEntity<UserResponseDTO> transactionNestedTest(@PathVariable long id,
                                                                 @RequestBody String name){
       return ResponseEntity.ok(testTTransactions.testNestedWithNameUpdater(id, name));
    }

    @GetMapping("/schedule")
    public ResponseEntity<String> scheduleTest(){
        return ResponseEntity.ok(testMethods.testScheduled());
    }
}
