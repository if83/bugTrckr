package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.repositories.LabelRepository;
import com.softserverinc.edu.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelRepository labelRepository;

    @Override
    public Label getOne(Long id) {
        return labelRepository.getOne(id);
    }

    @Override
    public Label save(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Override
    public void delete(Long id) {
        labelRepository.delete(id);
    }

    @Override
    public Label update(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Override
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

}
