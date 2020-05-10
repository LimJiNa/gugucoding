package com.jinalim.admin.board;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinalim.admin.Criteria;
import com.jinalim.admin.SearchCriteria;
import com.jinalim.admin.reply.ReplyDAO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO boardDao;
	
	@Transactional
	@Override
	public void regist(BoardVO vo) throws Exception {
		boardDao.create(vo);
		System.out.println(vo.toString());
		
		String[] files = vo.getFiles();
		
		if(files == null) {
			System.out.println("null");
			return;
		}
		
		for(String fileName : files) {
			System.out.println(fileName);
			boardDao.addAttach(fileName);
		}
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		boardDao.updateViewCnt(bno);
		return boardDao.read(bno);
	}

	@Transactional
	@Override
	public void modify(BoardVO vo) throws Exception {
		boardDao.update(vo);
		
		Integer bno = vo.getBno();
		
		boardDao.deleteAttach(bno);
		
		String[] files = vo.getFiles();
		
		if(files == null) {
			return;
		}
		
		for(String fileName : files) {
			boardDao.replaceAttach(fileName, bno);
		}
	}

	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		boardDao.deleteAttach(bno);
		boardDao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return boardDao.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return boardDao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		return boardDao.countPaging(cri);
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		return boardDao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return boardDao.listSearchCount(cri);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return boardDao.getAttach(bno);
	}
}
