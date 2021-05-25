package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

public class MainClass {
    public static void main(String[] args) {
        JsonService jsonService = new JsonService();

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/sv2/json/result?loose=4&solid=4&liquid=4";

        String jsonString = restTemplate.getForObject(url, String.class);
        Report report = jsonService.getReportFromJson(jsonString);

        for (Report.ReportElement rp : report.elements)
        {
            System.out.println(rp);
        }

        url = "http://localhost:8081/sv2/json/info";
        jsonString = restTemplate.getForObject(url, String.class);
        Report.Info info = jsonService.getInfoFromJson(jsonString);

        System.out.println(info);


    }
}
