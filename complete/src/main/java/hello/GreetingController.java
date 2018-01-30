package hello;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";

    @RequestMapping(value="/greeting", method = RequestMethod.POST)
    public Greeting greeting(@RequestBody Greeting greeting) {
        return new Greeting(greeting.getPhoneNumber(),
                            String.format(template, greeting.getMessage()));
    }
}
