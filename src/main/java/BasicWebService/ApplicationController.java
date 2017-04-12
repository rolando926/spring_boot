package BasicWebService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ApplicationController {

    private AtomicLong counter = new AtomicLong();
    private static final String template = "Hello, %s!";

    private static BigInteger nextId;
    private static Map<BigInteger, NewGreeting> greetingMap;

    private static NewGreeting save(NewGreeting greeting) {
        if (greetingMap == null) {
            greetingMap = new HashMap<>();
            nextId = BigInteger.ONE;
        }
        // If Update...
        /*
        if (BigInteger.valueOf(greeting.getId()) != null) {
            NewGreeting oldGreeting = greetingMap.get(greeting.getId());
            if (oldGreeting == null) {
                return null;
            }
            greetingMap.remove(greeting.getId());
            greetingMap.put(BigInteger.valueOf(greeting.getId()), greeting);
            return greeting;
        } */
        // If Create...
        greeting.setId(nextId.longValue());
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(BigInteger.valueOf(greeting.getId()), greeting);
        return greeting;
    }

    static {
        NewGreeting g1 = new NewGreeting();
        g1.setStrMessage("Hello World!");
        save(g1);

        NewGreeting g2 = new NewGreeting();
        g2.setStrMessage("Hola Mundo!");
        save(g2);
    }

    @RequestMapping(value="/new_greeting", method = RequestMethod.GET)
    public ResponseEntity<Collection<NewGreeting>> getGreeting(){
        Collection<NewGreeting> greetings = greetingMap.values();
        return new ResponseEntity<Collection<NewGreeting>>(greetings,
                HttpStatus.OK);
    }

    @RequestMapping(
            value = "/new_greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewGreeting> getGreeting(
            @PathVariable("id") BigInteger id) {

        NewGreeting greeting = greetingMap.get(id);
        if (greeting == null) {
            return new ResponseEntity<NewGreeting>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<NewGreeting>(greeting, HttpStatus.OK);
    }


    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting getGreeting(@RequestParam(value="name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }


}
