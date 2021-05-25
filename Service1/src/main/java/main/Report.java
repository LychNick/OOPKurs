package main;

import com.google.gson.annotations.Expose;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class Report {

    public static class Info
    {
        @Expose
        public int looseCranes;
        @Expose
        public int solidCranes;
        @Expose
        public int liquidCranes;
        @Expose
        public int numberOfShips;
        @Expose
        public int midLength;
        @Expose
        public int midWait;
        @Expose
        public int maxLate;
        @Expose
        public int midLate;
        @Expose
        public int penalty;


        @Override
        public String toString() {
            return "Info{" +
                    "looseCranes=" + looseCranes +
                    ", solidCranes=" + solidCranes +
                    ", liquidCranes=" + liquidCranes +
                    ", numberOfShips=" + numberOfShips +
                    ", midLength=" + midLength +
                    ", midWait=" + midWait +
                    ", maxLate=" + maxLate +
                    ", midLate=" + midLate +
                    ", pinalty=" + penalty +
                    '}';
        }
    }
    public Info info;

    public Report()
    {
        info = new Info();
        info.looseCranes = 0;
        info.liquidCranes = 0;
        info.solidCranes = 0;
        info.midLength = 0;
        info.numberOfShips = 0;
        info.maxLate = 0;
        info.midLate = 0;
        info.midWait = 0;
        info.penalty = 0;

    }

    //public final Info info = new Info();
    static class ReportElement
    {
        public ReportElement(){};
        public ReportElement(Ship ship, ArrivalTime arrivalTime)
        {
            this.ship = ship;
            this.arrivalTime = arrivalTime;
        }

        @Override
        public String toString() {
            return "ReportElement{" +
                    "ship=" + ship +
                    ", arrivalTime=" + arrivalTime +
                    '}';
        }

        @Expose
        public Ship ship;
        @Expose
        public ArrivalTime arrivalTime;
    }
    public ArrayList<ReportElement> elements = new ArrayList<>();
}
