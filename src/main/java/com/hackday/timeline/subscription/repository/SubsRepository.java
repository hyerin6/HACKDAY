package com.hackday.timeline.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackday.timeline.subscription.domain.Subscription;

public interface SubsRepository extends JpaRepository<Subscription, Long> {

}
