package hello;

import com.amazonaws.services.sns.model.PublishResult;

public class Greeting {

    private long phoneNumber;
    private String message;
	private String accessKey;
    private String secretKey;
    private PublishResult result;
	public Greeting(long phoneNumber, String message, PublishResult result) {
		super();
		this.phoneNumber = phoneNumber;
		this.message = message;
		this.result = result;
	}
    
    public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public Greeting(long phoneNumber, String message) {
		super();
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
		public Greeting() {
		super();
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public PublishResult getResult() {
		return result;
	}

	public void setResult(PublishResult result) {
		this.result = result;
	}
    
    

   
}