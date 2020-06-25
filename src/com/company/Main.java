package com.company;

import com.company.exceptions.FileWithIpsNotFoundException;
import com.company.util.CounterOfUniqueIps;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        long m = System.currentTimeMillis();
        CounterOfUniqueIps counterOfUniqueIps = new CounterOfUniqueIps();
        try {
            System.out.println(counterOfUniqueIps.getCount(""));
            System.out.println((double) (System.currentTimeMillis() - m) / 1000);
        } catch (FileWithIpsNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
