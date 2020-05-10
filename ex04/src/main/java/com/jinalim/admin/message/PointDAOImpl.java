package com.jinalim.admin.message;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO {

	@Inject
	private SqlSession sqlSession;

	public static final String namespace = "com.jinalim.aop.mappers.pointMapper";

	@Override
	public void updatePoint(String userid, int point) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("point", point);

		sqlSession.update(namespace + ".updatePoint", paramMap);
	}

}
