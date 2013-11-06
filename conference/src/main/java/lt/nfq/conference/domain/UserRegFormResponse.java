package lt.nfq.conference.domain;

import java.util.List;

import org.springframework.validation.ObjectError;

public class UserRegFormResponse {
	private String status;
	private List<ObjectError> result;

	public UserRegFormResponse() {
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

}
