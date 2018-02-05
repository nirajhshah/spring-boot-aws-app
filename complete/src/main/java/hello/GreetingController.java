package hello;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishResult;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    @RequestMapping(value="/greeting", method = RequestMethod.POST)
    public Greeting greeting(@RequestBody Greeting greeting) {
        
        String message = greeting.getMessage();
        String phoneNumber =  "+1" + greeting.getPhoneNumber();
        
        AmazonSNSClient snsClient = Service.createNewClient();
        System.out.println(" client built");
        Map<String, MessageAttributeValue> smsAttributes = Service.createSmsAttributes();
        System.out.println(" attributes built");
        PublishResult result = Service.sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
        System.out.println(" result: " + result.toString());
        return new Greeting(greeting.getPhoneNumber(),
                String.format(template, greeting.getMessage() ), result);
    }
}
