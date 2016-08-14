package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Label findByTitle(String title);

    /*set issue*/

}
