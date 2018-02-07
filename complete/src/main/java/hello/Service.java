package hello;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Service {
	
	private static final String CLASS_NAME = "Service";
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
	public static AmazonSNSClient createNewClient(Greeting greeting) 
	{
		String phoneNumber = "+1" + greeting.getPhoneNumber();
		LOGGER.log(Level.INFO, " [sendAlert] createNewClient - START Phone Number: " + phoneNumber);
        BasicAWSCredentials credentials = new BasicAWSCredentials(greeting.getAccessKey(), greeting.getSecretKey());
		@SuppressWarnings("deprecation")
		AmazonSNSClient snsClient = new AmazonSNSClient(credentials).withRegion(Regions.US_EAST_1);
        LOGGER.log(Level.INFO, " [sendAlert] createNewClient - END Phone Number: " + phoneNumber);
        return snsClient;
	}

	public static PublishResult sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) 
	{
		LOGGER.log(Level.INFO, " [sendAlert] sendSMSMessage - START Phone Number: " + phoneNumber);

	    PublishRequest request = new  PublishRequest();
	    request.putCustomRequestHeader("Access-Control-Allow-Origin", "*");
		PublishResult result = snsClient.publish(request
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
		LOGGER.log(Level.INFO, " [sendAlert] sendSMSMessage - Result: " + result.toString() + " Phone Number: " + phoneNumber);
	        return result;
	}
	
	public static Map<String, MessageAttributeValue> createSmsAttributes()
	{
		LOGGER.log(Level.INFO, " [sendAlert] createSmsAttributes - START");
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
		LOGGER.log(Level.INFO, " [sendAlert] createSmsAttributes - END");
		return smsAttributes;
	}

}
