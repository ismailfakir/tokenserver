package net.cloudcentrik.autolink.tokenserver.exception;

public class UserCollectionException {

    private static final long serialVersionUID = 1L;

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String id){
            super("User with "+id+" not found!");
        }
    }

    public static class UserAlreadyExists extends Exception {
        public UserAlreadyExists(String email){
            super("User with given email: "+email+" already exists!");
        }
    }

    public static class InvalidData extends Exception {
        public InvalidData(String message){
            super(message);
        }
    }

    public static class UnknownError extends Exception {
        public UnknownError(Throwable err) {
            super("something went wrong, cause unknown!",err);
        }
    }

}
