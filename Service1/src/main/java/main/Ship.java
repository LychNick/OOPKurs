package main;

import com.google.gson.annotations.Expose;

import java.util.*;

public class Ship{
    public enum Type
    {
        LOOSE,
        LIQUID,
        SOLID
    }
    @Expose
    public Type cargoType;
    @Expose
    public int startWeight;
    @Expose
    public String name;

    public int weight;
    public int late;

    @Expose
    public double unloadingTime = 0;
    @Expose
    public int penalty = 0;


    public int craneCount = 0;

    private static final Random random = new Random();

    private static  final int minWeight = 100;
    private static  final int maxWeight = 500;

    @Override
    public String toString() {
        return "Ship{" +
                "cargoType=" + cargoType +
                ", startWeight=" + startWeight +
                ", name='" + name + '\'' +
                ", unloadingTime=" + unloadingTime +
                ", penalty=" + penalty +
                '}';
    }

    public Ship(String name, Type type, int weight)
    {
      this.name = name;
      this.startWeight = weight;
      this.weight = weight;
      this.cargoType = type;
    }

    public Ship()
    {
        this.name = getRandomName(5);
        this.weight = getRandomWidth();
        this.startWeight = weight;
        this.cargoType = getRandomType();
    }
    private String getRandomName(int size)
    {
        String vowels = "QWRTPSDFGHJKLZXCVBNM";
        String consonants = "EYUIOA";
        String generatedName = "";
        boolean isVow = true;
        for (int i =0;i < size;i++)
        {
            if (isVow) {
                generatedName += vowels.charAt(random.nextInt(vowels.length()));
            }
            else
                {
                    generatedName += consonants.charAt(random.nextInt(consonants.length()));
                }
            isVow = !isVow;
        }
        return generatedName;
    }
    private Type getRandomType()
    {
        int typeNumber = random.nextInt(3);
        switch (typeNumber)
        {
            case 0 :
            {
                return Type.LIQUID;
            }
            case 1 :
            {
                return Type.SOLID;
            }
            default :
            {
                return Type.LOOSE;
            }
        }
    }
    private int getRandomWidth()
    {
        return random.nextInt(maxWeight - minWeight) + minWeight;
    }
}
