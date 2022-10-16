package net.cloudcentrik.autolink.tokenserver.utils;

import net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException;
import org.springframework.http.ResponseEntity;

public interface ExecuteWithResponseEntity {

    ResponseEntity<?> wrapInResponseEntity() throws UserCollectionException.UserAlreadyExists, UserCollectionException.UserNotFoundException;

}
