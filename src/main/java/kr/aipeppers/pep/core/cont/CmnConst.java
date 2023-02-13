package kr.aipeppers.pep.core.cont;

public class CmnConst {

	public static final String PARAM_BOX = "paramBox";
	public static final String PARAM_BOX_ORG = "paramBoxOrg";

	public static final String REQ_DTO = "REQ_DTO";
	public static final String RES_DTO = "RES_DTO";

	public static final String PAGINATE = "PAGINATE";
	public static final String REQUEST_DATA = "REQUEST_DATA";
	public static final String REQUEST_DATA_EXCEPTION = "REQUEST_DATA_EXCEPTION";

	/* Project Gubun */
	public static final String APP_NAME_BO = "bo";
	public static final String APP_NAME_UI = "ui";

	/* Encode type */
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String UTF8 = "UTF-8";
	public static final String EUCKR = "EUC-KR";
	public static final String MS949 = "MS949";
	public static final String CHARSET = UTF8;

	/* Values */
	public static final String STR_EMPTY = "";
	public static final String STR_SPACE = " ";
	public static final String LOCAL_IP6 = "0:0:0:0:0:0:0:1";
	public static final String LOCAL_IP = "127.0.0.1";


	/* Session Key */
	public static final String SES_USER_DATA = "SES_USER_DATA";
	public static final String SES_USER_ID = "SES_USER_ID";

	/* Redis Biz Key */
	public static final String REDIS_CMN = "CMN:"; //REDIS 공통 영역 데이터
	public static final String REDIS_BIZ = "BIZ:"; //REDIS 업무 영역 임시 데이터

	/* Request Key */
	public static final String REQUEST_MENU = "REQUEST_MENU";
	public static final String REQUEST_MENU_ID = "REQUEST_MENU_ID";
	public static final String REQUEST_TOP_MENU_ID = "REQUEST_TOP_MENU_ID";

	/* paramBox property key */
	public static final String REQUEST_PARAM_BOX = "paramBox";
	public static final String REQUEST_MODEL_BOX = "modelBox";
	public static final String PARAM_PROP_API_LOG_BOX = "PARAM_PROP_API_LOG_BOX";
	public static final String PARAM_PROP_CLIENT_API_LOG_LIST = "PARAM_PROP_CLIENT_API_LOG_LIST";
	public static final String PARAM_PROP_THREAD_BOX = "PARAM_PROP_THREAD_BOX";
	public static final String PARAM_PROP_FILE_BOX = "PARAM_PROP_FILE_BOX";
	public static final String PARAM_HEADER_BOX = "headerBox";
	public static final String PARAM_PROP_MODEL_BOX = "PARAM_PROP_MODEL_BOX";
	public static final String PARAM_PROP_RQT_PARAM_BOX = "PARAM_PROP_RQT_PARAM_BOX";
	public static final String PARAM_PROP_RQT_DATA = "PARAM_PROP_RQT_DATA";  //request body (object)
	public static final String PARAM_PROP_SESSION = "PARAM_PROP_SESSION";
	public static final String PARAM_PROP_URL_BAS_BOX = "PARAM_PROP_URL_BAS_BOX";
	public static final String PARAM_PROP_RES_BOX = "PARAM_PROP_RES_BOX";  // itgResCd, resCd
	public static final String PARAM_PROP_JSON_FORMAT = "PARAM_PROP_JSON_FORMAT";  // response json format
	public static final String PARAM_PROP_VIEW = "PARAM_PROP_VIEW";
	public static final String PARAM_PROP_CHARSET = "PARAM_PROP_CHARSET";  // charset
	public static final String PARAM_PROP_STATUS_BOX = "PARAM_PROP_STATUS_BOX";  // status
	public static final String PARAM_PROP_SIMPLE_HTML_DATA_KEY = "PARAM_PROP_SIMPLE_HTML_DATA_KEY";

	/* paramBox key */
	public static final String PARAM_PAGINATE = "paginate";
	public static final String PARAM_PAGINATE_PAGE = "page";
	public static final String PARAM_PAGINATE_SIZE = "pageSize";
	public static final String PARAM_PAGINATE_SORT_COLUMN = "sortColumn";
	public static final String PARAM_PAGINATE_SORT_ORDER = "sortOrder";
	public static final String PARAM_PAGINATE_SUFFIX = "PARAM_PAGINATE_SUFFIX";
	public static final String PARAM_PAGINATE_RECORD_SIZE = "PARAM_PAGINATE_RECORD_SIZE";
	public static final String PARAM_PAGINATE_PAGE_SIZE = "PARAM_PAGINATE_PAGE_SIZE";
	public static final String PARAM_PAGINATE_TOTAL_PAGE = "totalPage";

	/* HTTP Header Key */
	public static final String HTTP_HEADER_ACCEPT = "Accept";
	public static final String HTTP_HEADER_ACCEPT_CHARSET = "Accept-Charset";
	public static final String HTTP_HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	public static final String HTTP_HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
	public static final String HTTP_HEADER_SCRN_ID = "SCRN_ID";
	public static final String HTTP_HEADER_API_COMMON = "API_COMMON";

	/* Excel */
	public static final String EXCEL_NAME = "EXCEL_NAME";
	public static final String EXCEL_COLUMN = "EXCEL_COLUMN";
	public static final String EXCEL_LIST = "EXCEL_LIST";
	public static final String EXCEL_SEARCH = "EXCEL_SEARCH";
	public static final String EXCEL_ETC = "EXCEL_ETC";

	/* ModelBox key */
	public static final String MODEL_DOWNLOAD_FILE_NAME = "MODEL_DOWNLOAD_FILE_NAME";
	public static final String MODEL_DOWNLOAD_FILE = "MODEL_DOWNLOAD_FILE";
	public static final String MODELMAP_XML_RESULT = "MODELMAP_XML_RESULT";

	/* Response code */
	public static class ResCd {
		public static final String RES_CD_SUCCESS = "NCMNM0001";  //성공
		public static final String RES_VALIDATION = "ESMNM0001";  //유효성 검증 오류
		public static final String RES_CD_400 = "ESMNM0002";  //Bad Request
		public static final String RES_CD_UNAUTHORIZED = "ESMNM0003";  //UNAUTHORIZED
		public static final String RES_CD_FORBIDDEN = "ESMNM0004";  //FORBIDDEN
		public static final String RES_CD_NOT_FOUND = "ESMNM0005";  //NOT FOUND
//		public static final String RES_UNPROCESSABLE_ENTITY = "ESMNM0006";  //UNPROCESSABLE_ENTITY
		public static final String RES_CD_ERROR = "ESMNM0007";  //시스템오류 - {0}
		public static final String RES_CD_SQL_ERROR = "ESMNM0008";  //SQL 실행오류 ({0})
		public static final String RES_CD_SESSION_OUT = "ESMNM0009";  //세션 만료
		public static final String RES_CD_DUPLICATE = "ESMNM0010";  //중복 오류
//		public static final String RES_CD_GATEWAY_TIMEOUT = "ESMNM0011";  //Gateway Timeout
		public static final String RES_CD_IF_ERROR = "ESMNM0012";  //{0} 연동 오류
		public static final String BIZ_ERROR = "ETEMP0001";  //{0}

		//	ESMNM0013	Content-Type 오류
		//	ESMNM0014	입력 데이터 포맷 오류
		//	ESMNM0015	{0} - 필수 항목 오류
		//	ESMNM0016	{0} - 숫자 타입 오류
		//	ESMNM0017	{0} - 날짜 패턴 오류 ({1})
		//	ESMNM0018	{0} - 최소길이 오류 (more than {1})
		//	ESMNM0019	{0} - 최대길이 오류 (less than {1})
		//	ESMNM0020	{0} - 유효값 오류 (more than {1},  less than {2})
		//	ESMNM0021	{0} - 패턴 오류
		//	ESMNM0022	{0} - 최소 Byte 길이 오류 (more than {1})
		//	ESMNM0023	{0} - 최대 Byte 길이 오류 (less than {1})
		//	ESMNM0024	필드 '{0}'의 일치항목이 [{1}]에 존재하지 않습니다.
		//	ESMNM0025	{0} - 최소값 오류 (more than {1})
		//	ESMNM0026	{0} - 최대값 오류 (less than {1})
		//	ESMNM0027	{0} - 유효성조건 오류
		//	ESMNM0028	일시중지된 서비스
		//	ESMNM0029	사용만료된 서비스

		//	EBMNM0001	사용자 정보가 존재하지 않습니다.
		//	EBMNM0002	로그인 세션이 존재하지 않습니다.
		//	EBMNM0003	첨부파일 등록이 실패하였습니다.
		//	EBMNM0004	첨부가능 파일수({0})를 초과했습니다.
		//	EBMNM0005	허용({0})되지 않은 첨부파일({1}) 형식입니다.
		//	EBMNM0006	첨부가능 용량({0})을 초과했습니다.
		//	EBMNM0007	해당 그룹코드에 상세코드가 존재합니다.
		//	EBMNM0008	{0} 존재하지 않습니다.
		//	EBMNM0009	유효하지 않은 접근입니다.
		//	EBMNM0010	권한이 없습니다.
		//	EBMNM0011	잘못된 엑셀 파일입니다. {0}
		//	EBMNM0012	사용할 수 없는 EMAIL입니다.
		//	EBMNM0013	엑셀 오류입니다. - [{0}번째 행, {1}번째 컬럼의 값 {2} : {3}]
	}

	public static final String AES_ENCRYPT_KEY = "MDEyMzQ1Njc4OUFCQ0RFRg==";  //암호화 키(encrypt key)

	public static final String PROFILE_KEY = "spring.config.activate.on-profile";
	public static final String PROFILE_DEFAULT = "local";
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_TEST = "test";
	public static final String PROFILE_STAG = "stag";
	public static final String PROFILE_PROD = "prod";

	public static final String JWT_TYP = "JWT";
	public static final String JWT_ALG = "HS256";
}