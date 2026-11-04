package practice_02._0._6.koushik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import practice_02._0._6.koushik.dto.UserResponseDTO;
import practice_02._0._6.koushik.entity.UserEntity;
import practice_02._0._6.koushik.exception.ResourceNotFoundException;
import practice_02._0._6.koushik.mapper.Mapper;
import practice_02._0._6.koushik.repository.AuditRepository;
import practice_02._0._6.koushik.repository.UserRepository;

@Service
public class TestTTransactions {
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;
    private final AuditService auditService;
    private final UserService userService;
    private final TestMethods testMethods;
    private final Mapper mapper;

    public TestTTransactions(UserRepository userRepository, AuditRepository auditRepository, AuditService auditService, UserService userService, TestMethods testMethods, Mapper mapper) {
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
        this.auditService = auditService;
        this.userService = userService;
        this.testMethods = testMethods;
        this.mapper = mapper;
    }

    public void testSupportsWithoutTransaction() {
        auditService.getLogsByUser(1L);
    }

    public String testingMandatory(){
        return testMethods.Mandatorytester();
    }

    @Transactional
    public String testingNever(){
        testMethods.testNever();
        return "Never Executed";
    }

    @Transactional
    public void testingNotSupported(){
        boolean active =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println("Before call: " + active);
        testMethods.testNotSupported();

        boolean active2 =
                TransactionSynchronizationManager.isActualTransactionActive();

        System.out.println("After Call: " + active2);
    }

    @Transactional
    public UserResponseDTO testNestedWithNameUpdater(long id, String name){
        testMethods.testNested(id, name);
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No User Found with id: "+id));
        entity.setName(name);
        userRepository.save(entity);
        testMethods.testNested(id,name);
        return mapper.entityToResponseDTO(entity);

    }

    public void testSchedulePrint(){
       testMethods.getTestResult();
    }
}
