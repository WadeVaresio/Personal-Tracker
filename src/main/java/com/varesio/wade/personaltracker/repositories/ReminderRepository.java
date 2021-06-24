package com.varesio.wade.personaltracker.repositories;

import com.varesio.wade.personaltracker.entities.Reminder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long> {
    List<Reminder> findAllByUserId(String userId);

    List<Reminder> findByLink(String link);
}
