package net.cloudcentrik.autolink.tokenserver.service;

import static net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException.*;
import net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException;
import net.cloudcentrik.autolink.tokenserver.model.User;
import org.springframework.data.domain.Page;

import java.lang.UnknownError;
import java.util.List;

public interface UserService {

    public List<User> getAllUsers() throws UnknownError;

    public Page<User> getAllUsersPaged(int page, int size) throws UnknownError;

    public User findUserById(String id) throws UserNotFoundException;

    public User createUser(User user) throws UserAlreadyExists;

    public User updateUser(String id, User user) throws UserNotFoundException, InvalidData;

    public void deleteUserById(String id) throws UserNotFoundException;
}
