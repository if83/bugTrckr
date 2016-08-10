package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
