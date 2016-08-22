package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findByEmailContaining(String email) {
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User> findByProject(Project project) {
        return userRepository.findByProject(project);
    }

    @Override
    public List<User> findByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    @Override
    public List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName) {
        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName);
    }

    @Override
    public List<User> findByFirstNameContaining(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    @Override
    public List<User> findByLastNameContaining(String lastName) {
        return userRepository.findByLastNameContaining(lastName);
    }

    @Override
    public List<User> findByIsDeleted(Boolean isDeleted) {
        return userRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.findOne(id).setIsDeleted(true);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
