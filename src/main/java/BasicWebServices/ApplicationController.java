package BasicWebServices;

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
    private static BigInteger oldId;
    private static Map<BigInteger, NewGreeting> greetingMap;
    private static boolean blnUpdate = false;

    static {
        NewGreeting g1 = new NewGreeting();
        g1.setStrMessage("Hello World!");
        save(g1);

        NewGreeting g2 = new NewGreeting();
        g2.setStrMessage("Hola Mundo!");
        save(g2);
    }

    private static NewGreeting save(NewGreeting greeting) {
        if (greetingMap == null) {
            greetingMap = new HashMap<BigInteger, NewGreeting>();
            nextId = BigInteger.ONE;
        }
        // If Update...
        if ((BigInteger.valueOf(greeting.getId()) != null) && blnUpdate) {
            blnUpdate = false;
            oldId = BigInteger.valueOf(greeting.getId());
            NewGreeting oldGreeting = greetingMap.get(oldId);
            if (oldGreeting == null) {
                return null;
            }
            //greetingMap.remove(BigInteger.valueOf(greeting.getId()));
            greetingMap.remove(oldGreeting);
            greetingMap.put(oldId, greeting);
            return greeting;
        }
        // If Create...
        greeting.setId(nextId.longValue());
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(BigInteger.valueOf(greeting.getId()), greeting);
        return greeting;
    }

    private static boolean delete(BigInteger id) {
        NewGreeting deletedGreeting = greetingMap.remove(id);
        if (deletedGreeting == null) {
            return false;
        }
        return true;
    }


    // Day 1: Create a simple service to display phrase and/or display name from param
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting getGreeting(@RequestParam(value="name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),String.format(template, name));
    }

    // Day 2: Create a more robust GET Service that gets info from Collection
    @RequestMapping(value="/new_greeting", method = RequestMethod.GET)
    public ResponseEntity<Collection<NewGreeting>> getGreeting(){
        Collection<NewGreeting> greetings = greetingMap.values();
        return new ResponseEntity<Collection<NewGreeting>>(greetings,
                HttpStatus.OK);
    }

    // Day 2: Create a GET Service that gets info from Collection based on id
    @RequestMapping(
            value = "/new_greeting/{id}",
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

    // Day 3: Create a POST call to add greetings into HashMap
    @RequestMapping(
            value = "/new_greeting",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewGreeting> createGreeting(
            @RequestBody NewGreeting greeting) {

        NewGreeting savedGreeting = save(greeting);

        return new ResponseEntity<NewGreeting>(savedGreeting, HttpStatus.CREATED);
    }

    // Day 3: Create a PUT call to update a greeting based on id
    @RequestMapping(
            value = "/new_greeting/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewGreeting> updateGreeting(
            @RequestBody NewGreeting greeting, @PathVariable("id") BigInteger id) {
        blnUpdate = true;
        greeting.setId(id.longValue());
        NewGreeting updatedGreeting = save(greeting);
        if (updatedGreeting == null) {
            return new ResponseEntity<NewGreeting>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<NewGreeting>(updatedGreeting, HttpStatus.OK);
    }

    // Day 4: Create the Delete method
    @RequestMapping(
            value = "/new_greeting/{id}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewGreeting> deleteGreeting(
            @PathVariable("id") BigInteger id, @RequestBody NewGreeting greeting) {

        boolean deleted = delete(id);
        if (!deleted) {
            return new ResponseEntity<NewGreeting>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<NewGreeting>(HttpStatus.NO_CONTENT);
    }

    // Day 4: Creating a nested JSON response
    @RequestMapping(
            value = "/students/associates",
            method = RequestMethod.GET)
    public ResponseEntity<Students> getStudents(){
        Map<String,String> map = new HashMap<>();
        Associates associates = new Associates();
        associates.setInstructor("Rolando Colon");
        associates.setClassName("SQ Training Pod 1");
        map.put("Archana","Associate");
        map.put("Sravanthi","Associate");
        map.put("Kinnari","Associate");
        map.put("Jay","Associate");
        map.put("Kimberly","Associate");
        associates.setAssociates(map);

        Map<String,String> map2 = new HashMap<>();
        Contractors contractors = new Contractors();
        associates.setInstructor("Rolando Colon");
        associates.setClassName("SQ Training Pod 1");
        map2.put("Thomas","Associate");
        map2.put("JP","Associate");
        map2.put("Sandhya","Associate");
        associates.setAssociates(map2);

        Students students = new Students();
        students.setAssociates(associates);
        students.setContractors(contractors);

        return new ResponseEntity<Students>(students,HttpStatus.OK);
    }

}
