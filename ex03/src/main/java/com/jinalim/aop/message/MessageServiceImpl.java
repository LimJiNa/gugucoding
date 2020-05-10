package com.jinalim.aop.message;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

	@Inject
	private MessageDAO messageDao;

	@Inject
	private PointDAO pointDao;

	@Transactional
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		messageDao.create(vo);
		pointDao.updatePoint(vo.getSender(), 10);
	}

	@Override
	public MessageVO readMessage(String userid, Integer mno) throws Exception {
		messageDao.updateState(mno);
		pointDao.updatePoint(userid, 5);
		return messageDao.readMessage(mno);
	}

}
