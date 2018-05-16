package hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishResult;

@RestController
public class GreetingController {

	private static final String CLASS_NAME = "GreetingController";
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


	private static String defaultSorrymessage = "Sorry you don't have any messages but remember that Just Us League is the best.";
	private static Map<Long, List<String>> messageMap = new ConcurrentHashMap<Long, List<String>>();
	private static Map<String, Long> userIdPhoneMap = new ConcurrentHashMap<String, Long>();
	List<String> namesnList = Arrays.asList("Shawn aka Flash", "Josh aka Aquaman", "Mujeeb aka Batman", "Sree aka Wonder Woman", "Niraj aka Superman");
	

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public Greeting greeting(@RequestBody Greeting greeting) {
		String messageStr = greeting.getMessage();
		String phoneNumber = "+1" + greeting.getPhoneNumber();
		List<String> msgList = messageMap.get(greeting.getPhoneNumber());
		if (msgList != null && !msgList.isEmpty() && msgList.size()>0) {
			msgList.add(messageStr);
		}
		else {
			msgList = new ArrayList<String>();
			msgList.add(messageStr);
		}
		messageMap.put(greeting.getPhoneNumber(), msgList);

		LOGGER.log(Level.INFO, " [sendAlert] greeting - START Phone Number: " + phoneNumber);

		AmazonSNSClient snsClient = Service.createNewClient(greeting);
		Map<String, MessageAttributeValue> smsAttributes = Service.createSmsAttributes();

		PublishResult result = Service.sendSMSMessage(snsClient, messageStr, phoneNumber, smsAttributes);

		LOGGER.log(Level.INFO, " [sendAlert] greeting - Status Code: " + result.getSdkHttpMetadata().getHttpStatusCode()
				+ " MessageId: " + result.getMessageId() + "Phone Number: " + phoneNumber);

		return new Greeting(greeting.getPhoneNumber(), messageStr);
	}

	
	@RequestMapping(value = "/greeting/myalerts", method = RequestMethod.POST)
	public Greeting greetingMyAlerts(@RequestBody Greeting greeting) {
		String msg = defaultSorrymessage;
		Long phone = userIdPhoneMap.get(greeting.getUserId());
		if (!StringUtils.isEmpty(phone)) {
			List<String> msgList = messageMap.get(phone);
			
			if (msgList != null && !msgList.isEmpty() && msgList.size()>0) {
				String name = namesnList.get(new Random().nextInt(namesnList.size()));
				msg = "Hello " + name +", Just us League brought you total " + msgList.size() + " alerts. ";
				
				String msgs = "";
				int messageNumber = 1;
				for(String msgFromList : msgList )
				{
					
					msgs = msgs +  msgFromList;
					
					if( messageNumber < msgList.size())
					{
						msgs = msgs + ". Next Alert, ";
					}
					messageNumber++;
				}
				msg = msg + msgs;
			
			}
		}
		else
		{
			phone = 1234L;
		}
		LOGGER.log(Level.INFO, " [getAlert] greeting - " + " Message: " + msg + "Phone Number: " + phone);

		return new Greeting(Long.valueOf(phone), msg);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@RequestBody Greeting greeting) {
		userIdPhoneMap.put(greeting.getUserId(), greeting.getPhoneNumber());

	}

}
