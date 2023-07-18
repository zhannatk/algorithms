package org.example;

import org.example.myArrays.MyStringListImpl;

public class Main {


    public static void main(String[] args) {


        System.out.println("STRING LIST");
        MyStringListImpl S1 = new MyStringListImpl(10);
        for (int ii = 0; ii < 50; ii++) {
            S1.add(String.valueOf(ii));
        }
        S1.remove("3");
        System.out.println(S1);
        S1.remove(3);
        System.out.println(S1);
        S1.remove(30);
        S1.set(46, "_46_");
        System.out.println(S1);
        System.out.println("22:" + S1.indexOf("22"));


    }
}