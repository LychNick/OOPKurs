package main;

import java.util.ArrayDeque;
import java.util.concurrent.CyclicBarrier;

public class Unloader {
    private final ScheduleGenerator schedule;
    public Unloader(ScheduleGenerator schedule)
    {
        this.schedule = schedule;
    }


    public Report unload(int looseCount, int solidCount, int liquidCount)
    {
        ArrayDeque<ScheduleGenerator.ScheduleElement> tempQueue = new ArrayDeque<>();
        for(ScheduleGenerator.ScheduleElement sh: schedule.timetable){
            tempQueue.addLast(sh);
        }
        Report newReport = Pier.reports;
        newReport.info.numberOfShips = tempQueue.size();

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(looseCount + solidCount + liquidCount);
        Pier loosePier = new Pier(looseCount, Ship.Type.LOOSE,cyclicBarrier);
        Pier solidPier = new Pier(solidCount, Ship.Type.SOLID, cyclicBarrier);
        Pier liquidPier = new Pier(liquidCount, Ship.Type.LIQUID ,cyclicBarrier);


        final ArrivalTime currentTime = tempQueue.getFirst().arrivalTime;

        while(currentTime.getDate() < 30)
        {
            if (!tempQueue.isEmpty()) {
                if (currentTime.compareTo(tempQueue.getFirst().arrivalTime) >= 0) {
                    switch (tempQueue.getFirst().ship.cargoType) {
                        case LOOSE -> loosePier.addShip(tempQueue.pop());
                        case SOLID -> solidPier.addShip(tempQueue.pop());
                        case LIQUID -> liquidPier.addShip(tempQueue.pop());
                    }
                }
            }
            while ((!loosePier.workDone()) && (!solidPier.workDone()) && (!liquidPier.workDone()))
            {
            }
            currentTime.plusMinute();
            loosePier.newIteration();
            solidPier.newIteration();
            liquidPier.newIteration();
        }

        solidPier.destroy();
        liquidPier.destroy();
        loosePier.destroy();


        System.out.println("Done");

        newReport.info.looseCranes = looseCount;
        newReport.info.solidCranes = solidCount;
        newReport.info.liquidCranes = liquidCount;
        newReport.info.penalty = Pier.Inform.sumOfPenalty / 60;

        newReport.info.midWait = Pier.Inform.sumOfPenalty * 60 / newReport.info.numberOfShips;

        newReport.info.midLength = Pier.Inform.maxLength / 2;
        newReport.info.maxLate = Pier.Inform.maxLate;
        newReport.info.midLate = (Pier.Inform.maxLate + Pier.Inform.minLate) / 2;


        return newReport;
    }
}
