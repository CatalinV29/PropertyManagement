package com.sda.propertyManager.service;

import com.sda.propertyManager.model.User;
import com.sda.propertyManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findById(Integer userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new Exception("No client record exist for given id");
        }
    }
    public List<User> findAll(Integer page, Integer size) {
        List<User> userList = new ArrayList<>();
        List<User> dbUser = userRepository.findAll(PageRequest.of(page, size)).getContent();
        for (User user : dbUser) {
            userList.add(user);
        }
        return userList;
    }

    public User createUser(User user) {
        if (user.getUserId() == null) {
            user = userRepository.save(user);
            return user;
        } else {
            Optional<User> optionalUser = userRepository.findById(user.getUserId());
            if (optionalUser.isPresent()) {
                User newUser = optionalUser.get();
                newUser.setUserName(user.getUserName());
                newUser.setPassword(user.getPassword());
                newUser.setEmail(user.getEmail());
                return newUser;
            } else {
                user = userRepository.save(user);

                return user;
            }
        }
    }

    public List<User> updateUser(Integer id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user1 = optionalUser.get();
            user1.setUserName(user.getUserName());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            userRepository.save(user1);
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(c -> {
                userList.add(c);
            });
            return userList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!", id));
        }
    }
    public List<User> deleteUser(Integer userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(userId);
            List<User> userList = new ArrayList<>();
            userRepository.findAll().forEach(c -> {
                userList.add(c);
            });
            return userList;
        } else {
            throw new UserNotFoundException(String.format("No user found with the id: %s!, id"));
        }
    }


    public User findByUserName(String username) {
        User user = userRepository.findUserByUserName(username);
        return user;
    }

}
