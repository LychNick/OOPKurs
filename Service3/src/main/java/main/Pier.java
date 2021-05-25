package main;

import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
public class Pier {

    static final ArrayList<ScheduleGenerator.ScheduleElement> ships = new ArrayList<>();
    public static final Report reports = new Report();
    public static final class Inform
    {
        public static int sumOfPenalty = 0;
        public static int maxLength = 0;
        public static int minLate = 1440;
        public static int maxLate = 0;
    }

    ArrayList<Boolean> isCountedCranes = new ArrayList<Boolean>();
    Ship.Type piereType;
    List<Thread> threads = new LinkedList<>();
    private final CyclicBarrier cyclicBarrier;

    private int cranesCount = 0;


    public boolean workDone()
    {
        int countedCranes = 0;
        for (int i =0; i < isCountedCranes.size();i++)
        {
            if (isCountedCranes.get(i))
            {
                countedCranes++;
            }
        }
        return countedCranes == cranesCount;
    }
    synchronized public void newIteration()
    {
        for (int i =0; i < isCountedCranes.size();i++)
        {
            isCountedCranes.set(i, false);
        }
    }

    public Pier(int cranes,Ship.Type piereType, CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
        this.cranesCount = cranes;
        this.piereType = piereType;
        for (int i = 0; i < cranes;i++)
        {
            Runnable worker = new Crane(piereType, i);

            isCountedCranes.add(false);
            threads.add(new Thread(worker));
            threads.get(i).start();
        }
    }
    public void destroy()
    {
        for (Thread tr : threads) {
            tr.interrupt();
        }
    }

    void addShip(ScheduleGenerator.ScheduleElement newShip)
    {
        newShip.ship.late = 0;//(int) (Math.random() * 1440) / 60;
        if (newShip.ship.late < Inform.minLate)
        {
            Inform.minLate = newShip.ship.late;
        }
        if (newShip.ship.late > Inform.maxLate)
        {
            Inform.maxLate = newShip.ship.late;
        }
        ships.add(newShip);
    }
    class Crane implements Runnable {
        private final Ship.Type craneType;
        public Crane(Ship.Type craneType, int counterIndex)
        {
            this.craneType = craneType;
            this.counterIndex = counterIndex;
        }
        private int counterIndex;

        synchronized void counter()
        {
            if (!isCountedCranes.get(counterIndex)) {
                isCountedCranes.set(counterIndex, true);
            }
        }
        final double speed = 2;

        @Override
        public void run() {
            boolean finding = true;
            int index = -1;
            while (!Thread.currentThread().isInterrupted()) {
                if (!isCountedCranes.get(counterIndex)) {
                    if (!finding) {
                        synchronized (ships) {

                            if (ships.get(index).ship.weight > 0) {
                                int currentLength= 0;
                                for (int i = index + 1; i < ships.size();i++)
                                {
                                    if (ships.get(i).ship.craneCount == 0) {
                                        if ((ships.get(i).ship.weight > 0)
                                                && (ships.get(i).ship.cargoType == craneType)) {
                                            currentLength++;
                                        }
                                        ships.get(i).ship.penalty += 1;
                                    }
                                }
                                if (currentLength > Inform.maxLength)
                                {
                                    Inform.maxLength = currentLength;
                                }
                                ships.get(index).ship.weight -= speed;
                                ships.get(index).ship.unloadingTime += 1. / ships.get(index).ship.craneCount;
                                if (ships.get(index).ship.weight <= 0)
                                {
                                    ships.get(index).ship.penalty /= 60;
                                    Inform.sumOfPenalty += ships.get(index).ship.penalty;
                                    reports.elements.add(new Report.ReportElement(
                                            ships.get(index).ship, ships.get(index).arrivalTime));
                                }
                            } else {
                                finding = true;
                            }
                        }
                    }
                    else
                    {
                        synchronized (ships) {
                            for (int i = 0; i < ships.size(); i++) {
                                if ((ships.get(i).ship.cargoType == this.craneType)
                                        && (ships.get(i).ship.craneCount < 2)
                                        && (ships.get(i).ship.weight > 0)) {
                                    index = i;
                                    ships.get(i).ship.weight += ships.get(i).ship.late * speed;
                                    finding = false;
                                    ships.get(i).ship.craneCount++;
                                    break;
                                }
                            }
                        }
                    }
                }
                counter();
            }
            //System.out.println(Thread.currentThread().getName() + " is shutdown");
        }
    }
}