package net.cloudcentrik.autolink.tokenserver.utils;

import net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

public class ResponseUtils {

    public static ResponseEntity<?> wrapResponse(ExecuteWithResponseEntity function) {
        try {
            return function.wrapInResponseEntity();
        } catch (UserCollectionException.UserAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserCollectionException.UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static ResponseEntity<?> wrapResponseNew(Callable<ResponseEntity<?>> callableObj) {
        try {
            return callableObj.call();
        } catch (UserCollectionException.UserAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserCollectionException.UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
