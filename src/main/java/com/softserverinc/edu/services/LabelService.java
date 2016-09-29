package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public Label findOne(Long id) {
        return labelRepository.findOne(id);
    }

    public Label saveNewLabel(String labelName) {
        Label label = new Label();
        label.setTitle(labelName);
        return save(label);
    }

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    @Transactional
    public Label save(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Transactional
    public void delete(Long id) {
        labelRepository.delete(id);
    }

    @Transactional
    public Label update(Label label) {
        return labelRepository.saveAndFlush(label);
    }


}
