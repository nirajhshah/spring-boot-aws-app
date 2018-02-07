package hello;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishResult;

@RestController
public class GreetingController 
{
	
	private static final String CLASS_NAME = "GreetingController";
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	
    private static final String template = "Hello, %s!";

    @RequestMapping(value="/greeting", method = RequestMethod.POST)
    public Greeting greeting(@RequestBody Greeting greeting) 
    { 		
        String message = greeting.getMessage();
        String phoneNumber =  "+1" + greeting.getPhoneNumber();
        
        LOGGER.log(Level.INFO, " [sendAlert] greeting - START Phone Number: " + phoneNumber);
        
        AmazonSNSClient snsClient = Service.createNewClient(greeting);
        Map<String, MessageAttributeValue> smsAttributes = Service.createSmsAttributes();
        
        PublishResult result = Service.sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
                
        LOGGER.log(Level.INFO, " [sendAlert] greeting - Status Code: " + result.getSdkHttpMetadata().getHttpStatusCode()
        		+ " MessageId: " + result.getMessageId() + "Phone Number: " + phoneNumber);
        
        return new Greeting(greeting.getPhoneNumber(),
                String.format(template, greeting.getMessage()));
    }
}
