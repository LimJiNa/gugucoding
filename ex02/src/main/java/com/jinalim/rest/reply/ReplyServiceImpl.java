package com.jinalim.rest.reply;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO replyDao;

	@Override
	public void addReply(ReplyVO vo) throws Exception {
		replyDao.create(vo);
	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return replyDao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		replyDao.update(vo);
	}

	@Override
	public void removeReply(Integer rno) throws Exception {
		replyDao.delete(rno);
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		return replyDao.listPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {
		return replyDao.count(bno);
	}

}
