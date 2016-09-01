package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.entities.enums.UserRole;
import com.softserverinc.edu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueService issueService;

    @Transactional
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public List<User> findByEmailContaining(String email) {
        return userRepository.findByEmailContaining(email);
    }

    @Transactional
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User findByEmailIs(String email) {
        return userRepository.findByEmailIs(email);
    }

    @Transactional
    public List<User> findByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User> findByRoleNotAndIsDeletedFalse(UserRole role) {
        return userRepository.findByRoleNotAndIsDeletedFalse(role);
    }

    @Transactional
    public List<User> findByProject(Project project) {
        return userRepository.findByProject(project);
    }

    @Transactional
    public List<User> findByProjectAndIsDeletedFalseAndEnabledIs(Project project, int enabled) {
        return userRepository.findByProjectAndIsDeletedFalseAndEnabledIs(project, enabled);
    }

    @Transactional
    public Page<User> findByProjectAndIsDeletedFalseAndEnabledIs(Project project, int enabled, Pageable pageable) {
        return userRepository.findByProjectAndIsDeletedFalseAndEnabledIs(project, enabled, pageable);
    }

    public List<User> findByNotAssignedToIssue() {
        List<User> result = new ArrayList<>();
        for(User user: findByRoleNotAndIsDeletedFalse(UserRole.ROLE_ADMIN)) {
            if(issueService.findByAssignee(user).isEmpty()) {
                result.add(user);
            }
        }
        return result;
    }

    @Transactional
    public List<User> findByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    @Transactional
    public List<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName) {
        return userRepository.findByFirstNameContainingAndLastNameContaining(firstName, lastName);
    }

    @Transactional
    public List<User> findByFirstNameContaining(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    @Transactional
    public List<User> findByLastNameContaining(String lastName) {
        return userRepository.findByLastNameContaining(lastName);
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Transactional
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('PROJECT_MANAGER')")
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Long count() {
        return userRepository.count();
    }

    @Transactional
    public Page<User> findByIsDeletedFalseAndEnabledIs(int enabled, Pageable pageable) {
        return userRepository.findByIsDeletedFalseAndEnabledIs(enabled, pageable);
    }

    @Transactional
    public List<User> findByEmailContainingAndRole(String email, UserRole role){
        return userRepository.findByEmailContainingAndRole(email, role);
    }

    @Transactional
    public Page<User> findByRole(UserRole role, Pageable pageable){
        return userRepository.findByRole(role, pageable);

    }
}
