package org.example;

import org.example.myArrays.MyIntegerList;
import org.example.myArrays.MyIntegerListImpl;
import org.example.myArrays.MyStringListImpl;

import java.util.*;

public class Main {

    public static void main(String[] args) {


        System.out.println();
        System.out.println();

        System.out.println("STRING LIST");
        MyStringListImpl S1 = new MyStringListImpl(10);
        for (int ii = 0; ii < 40; ii++) {
            S1.add(String.valueOf(ii));
        }
        System.out.println("S1.remove(\"3\");");
        S1.remove("3");
        System.out.println(S1);
        System.out.println("S1.remove(3);");
        S1.remove(3);
        System.out.println(S1);
        System.out.println("S1.remove(30); and  S1.set(36, \"_36_\");");
        S1.remove(30);
        S1.set(36, "_36_");
        System.out.println(S1);
        System.out.println("System.out.println(\"22:\" + S1.indexOf(\"22\"));");
        System.out.println("22:" + S1.indexOf("22"));


        System.out.println();
        System.out.println();
        System.out.println("INTEGER LIST:");

        MyIntegerList I1 = new MyIntegerListImpl(10);
        for (int iii = 0; iii < 40; iii++) {
            I1.add(iii);
        }
        System.out.println("I1.removeByItem(3);");
        I1.removeByItem(3);
        System.out.println(I1);
        System.out.println("I1.removeByIndex(3);");
        I1.removeByIndex(3);
        System.out.println(I1);
        System.out.println("I1.removeByIndex(30); and I1.set(36, 103600);");

        I1.removeByIndex(30);
        I1.set(36, 103600);
        System.out.println(I1);
        System.out.println("System.out.println(\"22:\" + I1.indexOf(22));");
        System.out.println("22:" + I1.indexOf(22));


        System.out.println("_________SORTING_____________");

        MyIntegerList mil1 = new MyIntegerListImpl(100001);
        MyIntegerList mil2 = new MyIntegerListImpl(100001);
        MyIntegerList mil3 = new MyIntegerListImpl(100001);
        MyIntegerList mil4 = new MyIntegerListImpl(100001);

        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100_000; i++) {
            int rndClone = rnd.nextInt(100000);
            mil1.add(rndClone);
            mil2.add(rndClone);
            mil3.add(rndClone);
            mil4.add(rndClone);
        }
        long start;
        Long time;

        long timeOfNoSortContains;
        long timeOfSortedContains;

        start = System.currentTimeMillis();
        mil1.contains(-1);
        timeOfNoSortContains = System.currentTimeMillis() - start;


        Map<Long, String> res = new TreeMap<>();

        System.out.print("Insertion: ");
        start = System.currentTimeMillis();
        mil1.sortInsertion();
        time = System.currentTimeMillis() - start;
        System.out.println(time);
        res.put(time, "Вставка");

        System.out.print("Bubble: ");
        start = System.currentTimeMillis();
        mil2.sortBubble();
        time = System.currentTimeMillis() - start;
        System.out.println(time);
        res.put(time, "Пузырёк");

        System.out.print("Selection: ");
        start = System.currentTimeMillis();
        mil3.sortSelection();
        time = System.currentTimeMillis() - start;
        System.out.println(time);
        res.put(time, "Выбор");

        System.out.print("QuickSort: ");
        start = System.currentTimeMillis();
        mil4.sortQuickSort();
        time = System.currentTimeMillis() - start;
        System.out.println(time);
        res.put(time, "Быстрая сортировка");


        System.out.println(res);


        start = System.currentTimeMillis();
        mil1.contains(-1);

        timeOfSortedContains = System.currentTimeMillis() - start;

        System.out.println();
        System.out.println("Бинарный поиск экономит " + (timeOfNoSortContains - timeOfSortedContains) + " мс");


    }
}