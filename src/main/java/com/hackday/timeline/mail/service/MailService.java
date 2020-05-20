package com.hackday.timeline.mail.service;

public interface MailService {

	public String getKey() throws Exception;

	public void mailSendWithMemberKey(String userId) throws Exception;

	public void alterMemberKey(Long mailNo) throws Exception;
}
