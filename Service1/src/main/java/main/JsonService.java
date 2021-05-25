package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonService
{
    private static Gson gson;

    public JsonService()
    {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
    public String getJson(@Nullable Object res)
    {
        return gson.toJson(res);
    }
    public ScheduleGenerator getScheduleFromJson(String jsonLine)
    {
        ScheduleGenerator schedule = new ScheduleGenerator();
        schedule.timetable = gson.fromJson(jsonLine, new TypeToken<List<ScheduleGenerator.ScheduleElement>>(){}.getType());;
        return schedule;
    }
    public Report getReportFromJson(String jsonLine)
    {

        Report report = new Report();
        report.elements = gson.fromJson(jsonLine, new TypeToken<List<Report.ReportElement>>(){}.getType());;
        return report;
    }
    public Report.Info getInfoFromJson(String jsonLine)
    {

        Report.Info info = new Report.Info();
        info= gson.fromJson(jsonLine, Report.Info.class);
        return info;
    }
}
