package main;

import com.google.gson.annotations.Expose;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ScheduleGenerator {

    public ArrayList<ScheduleElement> timetable = new ArrayList<>();

    public class ScheduleElement implements Comparable<ScheduleElement>
    {
        @Override
        public int compareTo(ScheduleElement element) {
            return arrivalTime.compareTo(element.arrivalTime);
        }

        public ScheduleElement()
        {
            ship = new Ship();
            arrivalTime = new ArrivalTime();
        }
        @Expose
        public Ship ship;
        @Expose
        public ArrivalTime arrivalTime;

    }
    public ScheduleGenerator()
    {
    }

    public void generate(int count)
    {
        timetable.clear();
        for (int i =0;i < count;i++)
        {
            timetable.add(new ScheduleElement());
        }
        Collections.sort(timetable);
    }

    public ArrayList<ScheduleElement> getTimetable() {
        return timetable;
    }

    public void setTimetable(ArrayList<ScheduleElement> timetable) {
        this.timetable = timetable;
    }
}
