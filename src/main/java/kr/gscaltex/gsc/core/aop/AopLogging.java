package kr.gscaltex.gsc.core.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.gscaltex.gsc.core.data.Box;
import kr.gscaltex.gsc.core.util.ConfigUtil;
import kr.gscaltex.gsc.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AopLogging {

	/** The Constant LOGGING_LINE. */
	private static final String LOGGING_CONTROLLER_BEFORE_LINE = "******************************  Controller Before Infomation  ******************************";

	/** The Constant LOGGING_LINE. */
	private static final String LOGGING_CONTROLLER_AFTER_LINE = "******************************  Controller After Infomation  ******************************";

//	private long startTimeMillis = 0L;

	@Value("${log.aspect}")
	private String logAspect;

	@Pointcut("execution(public * kr.gscaltex.gsc..*Controller..*(..))")
	public void logging() {}

	/**
	 * Controller before.
	 *
	 * @param joinPoint the join point
	 * @return the string
	 */
	@Before("logging()")
	public void controllerBefore(JoinPoint joinPoint) {
//		startTimeMillis = System.currentTimeMillis();
		if ("NONE".equals(logAspect) || "AFTER".equals(logAspect)) { return; }
		if ("BEFORE".equals(logAspect) || "BOTH".equals(logAspect)) {
			Signature signature = joinPoint.getSignature();
			String method = signature.getDeclaringTypeName().substring(signature.getDeclaringTypeName().lastIndexOf(".") + 1, signature.getDeclaringTypeName().length())
					+ "." + signature.getName();
			if (ConfigUtil.getString("log.except.method").indexOf(method) <= -1)  {
//			if (!"sessionInfo".equals(signature.getName())) {
				if (log.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					sb.append("\n")
						.append(LOGGING_CONTROLLER_BEFORE_LINE)
						.append("\n")
						.append(logging(joinPoint))
						.append("\n")
						.append(LOGGING_CONTROLLER_BEFORE_LINE);
					log.debug(sb.toString());
				}
			}
		}
	}

	/**
	 * Controller after.
	 *
	 * @param joinPoint the join point
	 * @return the string
	 */
	@After("logging()")
	public void controllerAfter(JoinPoint joinPoint) {
//		log.debug("spend time: {} ms", (System.currentTimeMillis() - startTimeMillis));
		if ("NONE".equals(logAspect) || "BEFORE".equals(logAspect)) { return; }
		if ("AFTER".equals(logAspect) || "BOTH".equals(logAspect)) {
			Signature signature = joinPoint.getSignature();
			String method = signature.getDeclaringTypeName().substring(signature.getDeclaringTypeName().lastIndexOf(".") + 1, signature.getDeclaringTypeName().length())
					+ "." + signature.getName();
			if (ConfigUtil.getString("log.except.method").indexOf(method) <= -1)  {
				if (log.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					sb.append("\n")
						.append(LOGGING_CONTROLLER_AFTER_LINE)
						.append("\n")
						.append(logging(joinPoint))
						.append("\n")
						.append(LOGGING_CONTROLLER_AFTER_LINE);
					log.debug(sb.toString());
				}
			}
		}
	}

	private String logging(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		StringBuffer logSb = new StringBuffer();

		String signatureStr = signature.toString();
		int paramStart = signatureStr.indexOf("(");
		String paramStr = null;
		if(paramStart > -1) {
			int paramEnd = signatureStr.lastIndexOf(")");
			if(paramEnd > -1) {
				paramStr = signatureStr.substring(paramStart+1, paramEnd);
			}
		}

		if (paramStr != null) {
			String[] param = paramStr.split(",");
			Object[] args = joinPoint.getArgs();
			int argsSize = args.length;
			if (argsSize == param.length) {
				logSb.append("URL : " + (null == SpringUtil.getRequestURL() ? "invoker alone\n" : SpringUtil.getRequestURL() + "\n"));
				logSb.append("Arguments : \n");
				for (int i = 0; i < argsSize; i++) {
					if ("Box".equals(param[i])) {
						String boxNm = "";
						if (argsSize == 2 && i == 0) {
							boxNm = "Box	";
						} else if (argsSize == 2 && i == 1) {
							boxNm = "Model";
						} else {
							boxNm = param[i];
						}
						Box box = (Box)args[i];
						if(box != null) {
							logSb.append("\t")
								.append(boxNm)
								.append(" : ");
							Box newBox = new Box();
							newBox.putAll(box);
							newBox.remove("sBox");
							logSb
								.append(newBox.entrySet())
								.append("\n");
						}
					} else {
						logSb.append("\t")
							.append(param[i])
							.append(" : ")
							.append(args[i])
							.append("\n");
					}
				}
			} else {
				logSb.append("URL : " + (null == SpringUtil.getRequestURL() ? "invoker alone\n" : SpringUtil.getRequestURL() + "\n"));
			}
			if (logSb.toString().length() > 0) {
				logSb.deleteCharAt(logSb.toString().length() - 1);
			}
		}
		return logSb.toString();
	}

}
