package queryBuilder.responses;

public class DataResponse {
	private ResponseTypes success;
	private String message;

	public ResponseTypes getSuccess() {
		return success;
	}

	public void setSuccess(ResponseTypes success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
