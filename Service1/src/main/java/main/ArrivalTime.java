package main;

import com.google.gson.annotations.Expose;

import java.util.*;

public class ArrivalTime implements Comparable<ArrivalTime>
{
    @Expose
    private int date;
    @Expose
    private int hour;
    @Expose
    private int minute;

    private static final Random random = new Random();

    public ArrivalTime()
    {
        setRandomTime();
    }

    @Override
    public String toString() {
        return "ArrivalTime{" +
                "date=" + date +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }

    private void setRandomTime() {
        date =  random.nextInt(29) + 1;
        hour =  random.nextInt(22) + 1;
        minute =  random.nextInt(58) + 1;
    }

    @Override
    public int compareTo(ArrivalTime arrivalTime) {
        if (date == arrivalTime.date)
        {
            if (hour == arrivalTime.hour)
            {
                return minute - arrivalTime.minute;
            }
            else
            {
                return hour - arrivalTime.hour;
            }
        }
        else
        {
            return date - arrivalTime.date;
        }
    }
    void plusDate()
    {
        if (++date > 30)
        {
            date = 1;
        }
    }
    void plusHour()
    {
        if (++hour >= 24)
        {
            hour = 0;
            plusDate();
        }
    }
    public void plusMinute()
    {
        if (++minute >= 60)
        {
            minute = 0;
            plusHour();
        }
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    /*public GregorianCalendar currentCalendar;

    public int getDate()
    {
        return currentCalendar.get(GregorianCalendar.DATE);
    }

    public int getHour()
    {
        return currentCalendar.get(GregorianCalendar.HOUR);
    }

    public  int getMinute()
    {
        return currentCalendar.get(GregorianCalendar.MINUTE);
    }

    ArrivalTime() {
        this.currentCalendar = getRandomTime();
    }


    private GregorianCalendar getRandomTime() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.DATE, (int)(1 + Math.random() * 30));
        calendar.set(GregorianCalendar.HOUR, (int)(1 + Math.random() * 24));
        calendar.set(GregorianCalendar.MINUTE, (int)(1 + Math.random() * 60));
        return calendar;
    }

    public int compareTo(ArrivalTime arrivalTime) {
        return currentCalendar.compareTo(arrivalTime.currentCalendar);
    }*/
}
