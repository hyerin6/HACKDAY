package com.hackday.timeline.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.subscription.domain.Subscription;

@Repository
public interface SubsRepository extends JpaRepository<Subscription, Long> {

}
