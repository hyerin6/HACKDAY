package com.hackday.timeline.mail.service;

public interface MailService {

	public String getKey() throws Exception;

	public void mailSendWithMemberKey(String userId) throws Exception;

	public void authMemberKey(String userId) throws Exception;

	public boolean checkKey(String key, String keyValue) throws Exception;
}
