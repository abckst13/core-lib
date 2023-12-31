package kr.aipeppers.pep.core.util.sms;

public class SMSConfig {
	/*********************************************************************/
	/**
	 * 아이코드 JSP 발송모듈 v3.0.2 b3
	 *
	 * 아이코드 서버 환경변수
	 *
	 * 아이코드 서버 입니다. 문자를 발송하기 위한 기본정보입니다.
	 * IP는 아이코드의 고정서버이니 변경하지 않으셔도 됩니다.
	 * 저의 아이코드의 고정서버 IP:Port는 '211.172.232.124:9201' 입니다.
	 * 서비스에 따라 포트번호가 달라질 수 있습니다.
	 */
	public static String	SOCKET_HOST		= "211.172.232.124";
	public static int		SOCKET_PORT		= 9201;

	/**
	 * 아이코드 접속 환경변수
	 *
	 * 아이코드 접속을 위한 토큰키입니다.
	 * 아이디와 패스워드는 아이코드 사이트인 'http://icodekorea.com/'의
	 * 기업고객페이지에서 생성된 토큰을 이용하시면 됩니다.
	 */
	public static String	TOKEN		= "77680a7452d88f86895097e1ce04978a";
	//public static String	TOKEN		= "b6680afa8584abf96eca803d38cd658d";
	public static String	ICODE_SEND_NUM		= "0263327272";
	/*********************************************************************/
}