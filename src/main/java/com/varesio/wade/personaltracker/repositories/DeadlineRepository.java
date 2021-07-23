package com.varesio.wade.personaltracker.repositories;

import com.varesio.wade.personaltracker.entities.Deadline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeadlineRepository extends CrudRepository<Deadline, Long> {
    List<Deadline> findAllByUserID(String userID);
}
