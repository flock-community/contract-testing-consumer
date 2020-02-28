package community.flock.contracttestingconsumer;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg)  {
        super(msg);
    }
}
