package com.jinalim.admin.message;

public interface MessageService {

	public void addMessage(MessageVO vo) throws Exception;
	
	public MessageVO readMessage(String userid, Integer mno) throws Exception;
	
}
