package com.varesio.wade.personaltracker.repositories;

import com.varesio.wade.personaltracker.entities.SavedLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedLinkRepository extends CrudRepository<SavedLink, Long> {
    List<SavedLink> findAllByUserId(String userId);
}
