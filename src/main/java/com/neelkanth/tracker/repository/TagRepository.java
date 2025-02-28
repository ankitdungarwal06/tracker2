package com.neelkanth.tracker.repository;

import com.neelkanth.tracker.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Optional<Tag> findByName(String tagName);
}
