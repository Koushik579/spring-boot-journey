package practice_02._0._6.koushik.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String errMsg){
        super(errMsg);
    }
}
