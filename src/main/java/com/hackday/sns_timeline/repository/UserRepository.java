package com.hackday.sns_timeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackday.sns_timeline.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
