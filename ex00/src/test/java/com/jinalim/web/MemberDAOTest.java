package com.jinalim.web;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jinalim.domain.MemberVO;
import com.jinalim.persistence.IMemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml" })
public class MemberDAOTest {
	@Inject
	private IMemberDAO dao;

	@Test
	public void testTime() throws Exception {
		System.out.println(dao.getTime());
	}

	@Test
	public void testInsertMember() throws Exception {
		MemberVO vo = new MemberVO();
		vo.setUserid("lim10sy");
		vo.setUserpw("rhaehfdl48");
		vo.setUsername("임소연");
		vo.setEmail("lim10sy@nate.com");
		
		dao.insertMember(vo);
	}
}
