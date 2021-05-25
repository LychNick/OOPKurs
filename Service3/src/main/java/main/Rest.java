package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/sv3")
public class Rest {

    @Autowired
    private Report report;

    @Autowired
    private JsonService jsonService;

    @GetMapping("/info")
    public ResponseEntity<String> getInfo()
    {
        System.out.println(report.info);
        String jsonResult = jsonService.getJson(report.info);

        return new ResponseEntity<>(jsonResult, HttpStatus.OK);

    }

    @GetMapping("/simulate")
    public ResponseEntity<String> simulate(HttpServletRequest request)
    {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/sv2/json/schedule";

        String jsonString = restTemplate.getForObject(url, String.class);

        ScheduleGenerator scheduleGenerator = jsonService.getScheduleFromJson(jsonString);
        Unloader unloader = new Unloader(scheduleGenerator);
        int looseCount = Integer.parseInt(request.getParameter("loose"));
        int solidCount = Integer.parseInt(request.getParameter("solid"));
        int liquidCount = Integer.parseInt(request.getParameter("liquid"));

        report = unloader.unload(looseCount,solidCount,liquidCount);

        String jsonResult = jsonService.getJson(report.elements);

        return new ResponseEntity<>(jsonResult, HttpStatus.OK);

    }

}
