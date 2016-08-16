package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import com.softserverinc.edu.repositories.LabelRepository;
import com.softserverinc.edu.services.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelRepository labelRepository;

    @Override
    public Label findOne(Long id) {
        return labelRepository.findOne(id);
    }

    @Override
    public Label findByTitle(String title) {
        return labelRepository.findByTitle(title);
    }

    @Override
    public List<Label> findByIssue(Issue issue) {
        List<Label> listOfAllLabels = labelRepository.findAll();
        List<Label> listOfLabels = null;
            for (Label labelIterator : listOfAllLabels) {
                if (labelIterator.getIssues().contains(issue)) {
                    listOfLabels.add(labelIterator);
                }
            }
            return listOfLabels;
    }

    @Override
    public List<Label> findByIsDeleted(Boolean isDeleted) {
        return labelRepository.findByIsDeleted(isDeleted);
    }

    @Override
    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    @Override
    @Transactional
    public Label save(Label label) {
        return labelRepository.saveAndFlush(label);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        labelRepository.findOne(id).setIsDeleted(true);
    }

    @Override
    @Transactional
    public Label update(Label label) {
        return labelRepository.saveAndFlush(label);
    }



}
