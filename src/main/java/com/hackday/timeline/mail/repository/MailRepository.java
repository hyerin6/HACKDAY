package com.hackday.timeline.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.mail.domain.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

}
