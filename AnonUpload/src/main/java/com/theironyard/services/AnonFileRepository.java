package com.theironyard.services;

import com.theironyard.entities.AnonFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jeff on 7/27/16.
 */
public interface AnonFileRepository extends JpaRepository <AnonFile, Integer> {
    AnonFile findFirstByPermanentFalseOrderByIdAsc();
    Long countByPermanentFalse();
    AnonFile findByFilename(String filename);
}
