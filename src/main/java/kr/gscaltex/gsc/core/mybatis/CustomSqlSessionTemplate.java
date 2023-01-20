package kr.gscaltex.gsc.core.mybatis;
/*
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import kr.gscaltex.gsc.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomSqlSessionTemplate extends SqlSessionTemplate {

	public CustomSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

//	@Override
//	public <E> List<E> selectList(String statement, Object parameter) {
//		return super.selectList(statement, BeanUtil.objConvertNullToDefault(parameter));
//	}

//	@Override
//	public <T> T selectOne(String statement, Object parameter) {
//		return super.selectOne(statement, BeanUtil.objConvertNullToDefault(parameter));
//	}

	@Override
	public int insert(String statement, Object parameter) {
		return super.insert(statement, BeanUtil.objConvertNullToDefault(parameter));
	}

	@Override
	public int update(String statement, Object parameter) {
		return super.update(statement, BeanUtil.objConvertNullToDefault(parameter));
	}

	@Override
	public int delete(String statement, Object parameter) {
		return super.delete(statement, BeanUtil.objConvertNullToDefault(parameter));
	}

}
*/