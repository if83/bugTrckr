package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.Label;

import java.util.List;

public interface LabelService {

    Label getOne(Long id);
    Label save(Label label);
    void delete(Long id);
    Label update(Label label);
    List<Label> getAll();

}
