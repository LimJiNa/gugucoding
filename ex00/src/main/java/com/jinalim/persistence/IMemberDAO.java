package com.jinalim.persistence;

import com.jinalim.domain.MemberVO;

public interface IMemberDAO {
	public String getTime();

	public void insertMember(MemberVO vo);

	public MemberVO readMember(String userid) throws Exception;

	public MemberVO readWithPW(String userid, String userpw) throws Exception;
}
