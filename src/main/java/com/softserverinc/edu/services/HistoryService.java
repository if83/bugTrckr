package com.softserverinc.edu.services;

import com.softserverinc.edu.entities.History;

import java.util.List;

public interface HistoryService {

    History getOne(Long id);
    History save(History history);
    void delete(Long id);
    History update(History history);
    List<History> getAll();

}
