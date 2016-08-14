package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.User;
import com.softserverinc.edu.repositories.UserRepository;
import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(propagation= Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    @Transactional(readOnly=false)
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly=false)
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    @Transactional(readOnly=false)
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }


    /**
     * Perform save or update transaction
     * @param user
     */
    @Override
    @Transactional(readOnly=false)
    public void saveOrUpdate(User user) {

        if (findOne(user.getId())==null) {
            save(user);
        } else {
            update(user);
        }
    }

}
