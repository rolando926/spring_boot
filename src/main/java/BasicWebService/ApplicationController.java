package BasicWebService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApplicationController {

    private AtomicLong counter = new AtomicLong();
    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public Greeting getGreeting(@RequestParam(value="name", defaultValue = "World!") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }
}