package com.cascv.oas.core.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 鏍囧噯鍖栬繑鍥炴暟鎹紝鎺ュ彛杩斿洖鐨勬暟鎹兘鎸夌収璇ョ被鏉ュ疄渚嬪寲
 * 鍓嶅悗绔垎绂诲紑鍙戯紝鏂逛究鍓嶇鍑嗙‘鑾峰彇鏁版嵁
 * 
 * */

public class ResponseData {

	private static final String OK = "ok";
	private static final String ERROR = "error";

	private Meta meta;
	private Map<String, Object> data;

	public ResponseData() {
		this.meta = new Meta();
		this.data = new HashMap<String, Object>();
	}

	public ResponseData putDataValue(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	public ResponseData success() {
		this.meta = new Meta(true, OK);
		return this;
	}

	public ResponseData success(String msg) {
		this.meta = new Meta(true, msg);
		return this;
	}

	public ResponseData success(String msg, Map<String, Object> data) {
		this.meta = new Meta(true, msg);
		this.data = data;
		return this;
	}

	public ResponseData failure() {
		this.meta = new Meta(false, ERROR);
		return this;
	}

	public ResponseData failure(int status, String msg, String code,
			String devMsg) {
		this.meta = new Meta(false, status, msg, code, devMsg);
		return this;
	}

	public class Meta {

		private boolean success;
		// 鐢ㄤ簬鍓嶇鍖哄埆涓嶅悓鐨勫紓甯镐俊鎭�
		// 鏂逛究鍓嶇閽堝涓嶅悓鐨剆tatus鐨勫�奸噰鍙栦笉鍚岀殑鎿嶄綔
		private int status;
		// 绠�鐭殑鎻愮ず淇℃伅
		private String msg;
		// 寮傚父缂栫爜
		private String code;
		// 寮�鍙戜汉鍛樻煡鐪嬬殑寮�鍙戜俊鎭�
		private String devMsg;

		public Meta() {
		}

		public Meta(boolean success) {
			this.success = success;
		}

		public Meta(boolean success, String msg) {
			this.success = success;
			this.msg = msg;
		}

		public Meta(boolean success, int status, String msg, String code,
				String devMsg) {
			this.success = success;
			this.status = status;
			this.msg = msg;
			this.code = code;
			this.devMsg = devMsg;
		}

		public boolean isSuccess() {
			return success;
		}

		public int getStatus() {
			return status;
		}

		public String getmsg() {
			return msg;
		}

		public String getCode() {
			return code;
		}

		public String getDevMsg() {
			return devMsg;
		}
	}

	public Meta getMeta() {
		return meta;
	}

	public Map<String, Object> getData() {
		return data;
	}
}
