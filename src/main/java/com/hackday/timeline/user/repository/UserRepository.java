package com.hackday.timeline.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackday.timeline.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
