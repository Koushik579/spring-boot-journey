package practice_02._0._6.koushik.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String errMsg){
        super(errMsg);
    }
}
