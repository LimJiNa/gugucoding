package com.jinalim.admin.reply;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinalim.admin.Criteria;
import com.jinalim.admin.board.BoardDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO replyDao;
	
	@Inject
	private BoardDAO boardDao;

	@Transactional
	@Override
	public void addReply(ReplyVO vo) throws Exception {
		replyDao.create(vo);
		boardDao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		return replyDao.list(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		replyDao.update(vo);
	}

	@Transactional
	@Override
	public void removeReply(Integer rno) throws Exception {
		int bno = replyDao.getBno(rno);
		replyDao.delete(rno);
		boardDao.updateReplyCnt(bno, -1);
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
