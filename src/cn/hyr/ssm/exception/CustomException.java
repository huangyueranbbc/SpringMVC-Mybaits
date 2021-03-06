package cn.hyr.ssm.exception;

/**
 * @category 自定义异常处理
 * @author huangyueran
 *
 */
public class CustomException extends Exception {

	// 异常信息
	public String message;

	public CustomException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
