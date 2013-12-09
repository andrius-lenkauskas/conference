package lt.nfq.conference.domain.webform;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ResponseForm {
	private String status;
	private String message;
	private List<ObjectError> result;

	public ResponseForm() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ObjectError> getResult() {
		return result;
	}

	public void setResult(List<ObjectError> result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
