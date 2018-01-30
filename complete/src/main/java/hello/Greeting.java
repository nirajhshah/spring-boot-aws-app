package hello;

public class Greeting {

    private long phoneNumber;
    private String message;
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
    
    

   
}
