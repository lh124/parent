package imageserver.util;

/**
 * @describe: 通过json对象返回数据
 * class_name: JsonResult
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-11-30  17:33
 **/
public class JsonResult {
	// 返回编码
	private Integer code;
	// 返回信息
	private String msg;

	public JsonResult() {
		code = 1;
		msg = "success";
	}

	public JsonResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{\"code\":" + code + ", \"msg\":\"" + msg + "\"}";
	}
 
}
