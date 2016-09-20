package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}
