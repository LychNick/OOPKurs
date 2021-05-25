package main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sv1")
public class Rest {

    @Autowired
    private ScheduleGenerator scheduleGenerator;

    @Autowired
    private JsonService jsonService;

    @GetMapping("/schedule")
    public ResponseEntity<String> getSchedule()
    {
        scheduleGenerator.generate(100);
        String jsonString = jsonService.getJson(scheduleGenerator.timetable);
        String response = jsonString;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
