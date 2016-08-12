package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getListOfAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getListOfUsersByRole(UserRole role) {
        return userRepository.getListOfUsersByRole(role);
    }

    @Override
    public List<User> getListOfUsersByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.getListOfUsersByFirstNameOrLastName(firstName, lastName);
    }

    @Override
    @Transactional
    public void update(Long id, User user) {
        userRepository.update( id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
                user.getRole(), user.getDescription());
    }

}
