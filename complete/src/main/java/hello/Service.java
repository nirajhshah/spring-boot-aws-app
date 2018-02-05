package hello;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Service {
	private static final String ACCESS_KEY = "AKIAJ3SGIIMYWW7A6DOA";
	private static final String SECRET_KEY = "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";
	
	
	
	public static AmazonSNSClient createNewClient() 
	{
		System.out.println(" Creating snsClient");
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		@SuppressWarnings("deprecation")
		AmazonSNSClient snsClient = new AmazonSNSClient(credentials).withRegion(Regions.US_EAST_1);
        
        return snsClient;
	}

	public static PublishResult sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) 
	{
		System.out.println(" sendSMSMessage");
	        PublishResult result = snsClient.publish(new PublishRequest()
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	
	        return result;
	}
	
	public static Map<String, MessageAttributeValue> createSmsAttributes()
	{
		System.out.println(" createSmsAttributes");
		Map<String, MessageAttributeValue> smsAttributes = 
	            new HashMap<String, MessageAttributeValue>();
		
		smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
		        .withStringValue("JUL") //The sender ID shown on the device.
		        .withDataType("String"));
		smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
		        .withStringValue("0.50") //Sets the max price to 0.50 USD.
		        .withDataType("Number"));
		smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
		        .withStringValue("Promotional") //Sets the type to promotional.
		        .withDataType("String"));
		
		return smsAttributes;
	}

}