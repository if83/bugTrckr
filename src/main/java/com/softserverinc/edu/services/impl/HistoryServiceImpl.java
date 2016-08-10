package com.softserverinc.edu.services.impl;

import com.softserverinc.edu.entities.History;
import com.softserverinc.edu.repositories.HistoryRepository;
import com.softserverinc.edu.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public History getOne(Long id) {
        return historyRepository.getOne(id);
    }

    @Override
    public History save(History history) {
        return historyRepository.saveAndFlush(history);
    }

    @Override
    public void delete(Long id) {
        historyRepository.delete(id);
    }

    @Override
    public History update(History history) {
        return historyRepository.saveAndFlush(history);
    }

    @Override
    public List<History> getAll() {
        return historyRepository.findAll();
    }

}
