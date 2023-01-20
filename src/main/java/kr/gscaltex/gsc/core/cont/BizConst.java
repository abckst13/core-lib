package kr.gscaltex.gsc.core.cont;

public class BizConst {

	public static class ProcessState {
		public static final String INIT = "INIT"; // 대기중
		public static final String WAIT = "WAIT"; // 시작
		public static final String PROGRESS = "PROGRESS"; // 처리중
		public static final String RETRY = "RETRY"; // 재시도
		public static final String FAIL = "FAIL"; // 실패
		public static final String DONE = "DONE"; // 처리완료
	}

	public static class Priority {
		public static final int HIGH = 30;
		public static final int MEDIUM = 20;
		public static final int LOW = 10;
	}

	public static class ThreadType {
		public static final String SCHEDULE = "SCH";
		public static final String MANUAL = "MUL";
		public static final String REST = "API";
	}

	public static class AES_KEYS {
		public static final String key_202010 = "aes-key-202010";
	}
	
	public static class CrudFlag {
		public static final String FLAG_C = "C";
		public static final String FLAG_I = "I";
		public static final String FLAG_R = "R";
		public static final String FLAG_U = "U";
		public static final String FLAG_D = "D";
	}

}
