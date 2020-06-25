package com.company.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class IpGenerator {
    private static final char SEPARATOR = '.';
    public static final int SEED = 1234;

    public void fill(String filePath, long ipsCount) {
        Random rnd = new Random(SEED);

        try (PrintWriter out = new PrintWriter(filePath)) {
            for (int i = 0; i < ipsCount; ++i) {
                for (int j = 0; j < 4; ++j) {
                    int part = rnd.nextInt(256);
                    out.print(part);
                    if (j + 1 < 4) out.print(SEPARATOR);
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
