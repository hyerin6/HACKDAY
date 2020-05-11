package com.hackday.sns_timeline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackday.sns_timeline.domain.Subscription;

public interface SubsRepository extends JpaRepository<Subscription, Long> {

}
