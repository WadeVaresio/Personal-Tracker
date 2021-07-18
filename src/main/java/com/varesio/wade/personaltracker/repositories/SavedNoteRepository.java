package com.varesio.wade.personaltracker.repositories;

import com.varesio.wade.personaltracker.entities.SavedNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedNoteRepository extends CrudRepository<SavedNote, Long> {
    List<SavedNote> findAllByUserID(String userID);
}
