package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.Release;
import com.softserverinc.edu.entities.enums.ReleaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {

    List<Release> findByProject(Project project);

    /*set issues*/

    List<Release> findByVersion(String version);

    List<Release> findByReleaseStatus(ReleaseStatus releaseStatus);

}
