package com.jinalim.admin.message;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDAOImpl implements MessageDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	public static final String namespace = "com.jinalim.aop.mappers.messageMapper";

	@Override
	public void create(MessageVO vo) throws Exception {
		sqlSession.insert(namespace + ".create", vo);
	}

	@Override
	public MessageVO readMessage(Integer mno) throws Exception {
		return sqlSession.selectOne(namespace + ".readMessage", mno);
	}

	@Override
	public void updateState(Integer mno) throws Exception {
		sqlSession.update(namespace + ".updateState", mno);
	}

}
