package kr.aipeppers.pep.core.mybatis;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import kr.aipeppers.pep.core.data.Box;
import kr.aipeppers.pep.core.util.DateUtil;
import kr.aipeppers.pep.core.util.HttpUtil;
import kr.aipeppers.pep.core.util.InvokerUtil;
import kr.aipeppers.pep.core.util.SessionUtil;
import kr.aipeppers.pep.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisLoggingInterceptor implements Interceptor {

//	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
//	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
//	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement)args[0];
		//TODO : application.yml에 향후 현행화
		String skipId = "biz.apiLogInsert,biz.queryLogInsert,biz.asyncQueryLogInsert"; //log출력을 제외할 id기재(','로 구분)
		String sql = "";
		String queryId = mappedStatement.getId();
		Object reqParam = "";
		String paramType = "";
		long time = 0;
		if (skipId.indexOf(queryId) == -1) {
			try {
				long start = System.currentTimeMillis();
				Object proceed = invocation.proceed();
				time = System.currentTimeMillis() - start;
	//			log.debug("proceed: {}", proceed);

				if (null != (Object)args[1]) {
					reqParam = (Object)args[1]; //파라메터
					paramType = args[1].getClass().getName(); //타입
				}
				sql = queryPrint(args, mappedStatement, sql, queryId, reqParam, paramType, time); //쿼리로그출력

				//쿼리결과 로그
				if (proceed != null) {
	//				if (proceed instanceof Map) {
	//					Map<String, Object> row = (HashMap<String, Object>) proceed;
	//					log.debug("result row: {}", row);
	//				} else if(proceed instanceof List) {
					if (proceed instanceof List) {
						List<Box> list = (List<Box>) proceed;
	//					if (list.size() > 0) {
			//				log.debug("Result: {}", list.get(0));
			//				for (Entry<String, String> entry : list.get(0).entrySet()) {
			//					String key = StringUtil.nvl(entry.getKey());
			//					if (!key.equals("")) {
			//						String value = ((Object) entry.getValue()).toString();
			//					}
			//				}
	//					}

						if (list.size() == 0) {
							log.debug("result: NO DATA");
						} else if (list.size() == 1) {
							log.debug("result: {}", list.get(0));
						} else {
							log.debug("total rows: {}", list.size());
						}
					}
				}
				return proceed;
			} catch(Exception e) {
				//에러시 쿼리출력이 안되므로 다시 쿼리를 재조립하여 로깅함 - 에러가 났기때문에 일부(소요시간등) 정보는 출력 불가능
				log.debug("[--------------QUERY ERROR---------------]");
				if (null != (Object)args[1]) {
					reqParam = (Object)args[1]; //파라메터
					paramType = args[1].getClass().getName(); //타입
				}
				sql = queryPrint(args, mappedStatement, sql, queryId, reqParam, paramType, 0);  //쿼리로그출력
				throw new Exception(e);
			} finally {
				/*
				Box logBox = new Box();
				Box resBox = new Box();
				logBox.put("memNm", mappedStatement.getId());
				logBox.put("authNo", time);
				logBox.put("passwd", reqParam.toString().length() > 200 ? reqParam.toString().substring(0, 200) : reqParam.toString());
				logBox.put("job", sql.length() > 200 ? sql.substring(0, 200) : sql);
				logBox.put("request", SpringUtil.getHttpServletRequest()); //async시 해당 메소드에서 request 객체가 존재하지 않으므로 호출전에 세팅해서 넘겨준다
//				InvokerUtil.methodCall("bizService.queryLogInsert", logBox, resBox); //sync
				InvokerUtil.methodCall("bizService.asyncQueryLogInsert", logBox, resBox); //async
//				log.debug("resBox : {}", resBox);
				*/
			}
		}

		return invocation.proceed();
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 쿼리로그출력
	 * @param args
	 * @param mappedStatement
	 * @param sql
	 * @param queryId
	 * @param reqParam
	 * @param paramType
	 * @param time
	 */
	public String queryPrint(Object[] args, MappedStatement mappedStatement, String sql, String queryId, Object reqParam, String paramType, long time) {
		if(null != args[1] && Box.class.isAssignableFrom(args[1].getClass())) {
			Box box = (Box)args[1];
			BoundSql boundSql = mappedStatement.getBoundSql(box);
			StringBuilder sqlSb = new StringBuilder(boundSql.getSql());
			for (ParameterMapping param : boundSql.getParameterMappings()) {
				String property = param.getProperty();
				int index = sqlSb.indexOf("?");
				if (box.getPath(property.replaceAll("\\.", "/")) != null) {
					sqlSb.replace(index, index + 1, "'" + box.getPath(property.replaceAll("\\.", "/")) + "'");
				} else {
					if (property.indexOf("_frch_") > -1) {
						sqlSb.replace(index, index + 1, "'" + boundSql.getAdditionalParameter(property) + "'");
					}else {
						sqlSb.replace(index, index + 1, "NULL");
					}
				}
			}
			sql = sqlSb.toString();

			log.debug("queryId: {}", queryId); //쿼리ID
			if (paramType.indexOf("data.Box") > -1) { //파라메터 type이 Box일 경우 sBox를 제거하고 출력한다.
				Box reqParamBox = new Box();
				reqParamBox.putAll((Box)reqParam);
				reqParamBox.remove("sBox");
				log.debug("reqParam: {}", reqParamBox);
			} else {
				log.debug("reqParam: {}", reqParam);
			}

			//쿼리내용
			if (time == 0) {
				log.debug("query: \n {}", sql);
			} else {
				log.debug("query: \n {} {spendTime: {} ms}", sql, time);
			}

		}
		return sql;
	}
}
