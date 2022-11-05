package net.cloudcentrik.autolink.tokenserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.cloudcentrik.autolink.tokenserver.exception.UserCollectionException;
import net.cloudcentrik.autolink.tokenserver.model.Role;
import net.cloudcentrik.autolink.tokenserver.model.User;
import net.cloudcentrik.autolink.tokenserver.repository.UserRepository;
import net.cloudcentrik.autolink.tokenserver.utils.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImple implements UserService {

    private final int defaultSize = 1000;
    private final int defaultPage = 0;
    private final Pageable defaultPaging = PageRequest.of(defaultPage, defaultSize, Sort.by(Sort.Direction.ASC, "userName"));

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() throws UnknownError {
        LOG.info("--[*]--> getting all users from database");
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users;
        }else {
            return new ArrayList<User>();
        }
    }

    @Override
    public Page<User> getAllUsersPaged(int page, int size) throws UnknownError {
        LOG.info("--[*]--> getting all users from database page:{},size:{}",page,size);
        Pageable paging = null;
        if(size < 0){
            paging = defaultPaging;
        } else {
            paging = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "userName"));
        }

        Page<User> users = userRepository.findAll(paging);
        return users;
    }

    @Override
    public User findUserById(String id) throws UserCollectionException.UserNotFoundException {
        LOG.info("--[*]--> finding user id: {} from database",id);
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional
                .orElseThrow(() -> new UserCollectionException.UserNotFoundException(id));
    }

    @Override
    public User createUser(User user) throws UserCollectionException.UserAlreadyExists {

        LOG.info("--[*]--> creating new user: {}",user);

        Optional<User> userOptional= userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent())
        {
            throw new UserCollectionException.UserAlreadyExists(user.getEmail());
        }
        else
        {
            user.setToken(new TokenGenerator().generateToken());
            return userRepository.save(user);
        }

    }

    @Override
    public User updateUser(String id, User userData) throws UserCollectionException.UserNotFoundException, UserCollectionException.InvalidData {
        LOG.info("--[*]--> updating user: id: {}",id);
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserCollectionException.UserNotFoundException(id);
        }else if(userData.getUserName() == null || userData.getPassword() == null){
            throw new UserCollectionException.InvalidData("userName or password missing");
        }else {
            User existingUser = userOptional.get();
            existingUser.setPassword(userData.getPassword());
            existingUser.setUserName(userData.getUserName());
            existingUser.setRole(userData.getRole());
            return userRepository.save(existingUser);
        }
    }

    @Override
    public void deleteUserById(String id) throws UserCollectionException.UserNotFoundException {

        LOG.info("--[*]--> deleting user: id: {}",id);

        Optional<User> userOptional = userRepository.findById(id);

         if (!userOptional.isPresent()) {
            throw new UserCollectionException.UserNotFoundException(id);
        }else {
             userRepository.deleteById(id);
        }


    }
}
