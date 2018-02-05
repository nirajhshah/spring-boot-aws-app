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
	
	
	public static AmazonSNSClient createNewClient(Greeting greeting) 
	{
		System.out.println(" Creating snsClient");
        BasicAWSCredentials credentials = new BasicAWSCredentials(greeting.getAccessKey(), greeting.getSecretKey());
		@SuppressWarnings("deprecation")
		AmazonSNSClient snsClient = new AmazonSNSClient(credentials).withRegion(Regions.US_EAST_1);
        
        return snsClient;
	}

	public static PublishResult sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) 
	{
		System.out.println(" sendSMSMessage");

	    PublishRequest request = new  PublishRequest();
	    request.putCustomRequestHeader("Access-Control-Allow-Origin", "*");
		PublishResult result = snsClient.publish(request
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
