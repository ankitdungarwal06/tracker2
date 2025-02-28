package com.neelkanth.tracker.repository;

import com.neelkanth.tracker.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
