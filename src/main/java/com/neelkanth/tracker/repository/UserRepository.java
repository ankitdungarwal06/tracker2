package com.neelkanth.tracker.repository;

import com.neelkanth.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
