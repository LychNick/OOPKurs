package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/sv2")
public class Rest {

    private static String jsonSchedule;

    RestTemplate restTemplate = new RestTemplate();

    public Rest()
    {
        String url = "http://localhost:8080/sv1/schedule";

        jsonSchedule = restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/json/schedule")
    public ResponseEntity<String> getSchedule()
    {
        return new ResponseEntity<>(jsonSchedule, HttpStatus.OK);
    }

    @GetMapping("/json/result")
    public ResponseEntity<String> getResult(HttpServletRequest request)
    {
        System.out.println(request.toString());
        int looseCount = Integer.parseInt(request.getParameter("loose"));
        int solidCount = Integer.parseInt(request.getParameter("solid"));
        int liquidCount = Integer.parseInt(request.getParameter("liquid"));
        String url = "http://localhost:8082/sv3/simulate?loose=" + looseCount + "&solid=" + solidCount + "&liquid=" + liquidCount ;

        jsonSchedule = restTemplate.getForObject(url, String.class);

        return new ResponseEntity<>(jsonSchedule, HttpStatus.OK);
    }

    @GetMapping("/json/info")
    public ResponseEntity<String> getInfo()
    {
        String url = "http://localhost:8082/sv3/info";

        jsonSchedule = restTemplate.getForObject(url, String.class);

        return new ResponseEntity<>(jsonSchedule, HttpStatus.OK);
    }

}
