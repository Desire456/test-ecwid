package com.company.util;

import com.company.exceptions.FileWithIpsNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;

public class CounterOfUniqueIps {
    private static final String FILE_NOT_FOUND_ERROR_MESSAGE = "File with ips not found";
    private static final char SEPARATOR = '.';
    private static final char ENDL_N = '\n';
    private static final char ENDL_R = '\r';
    private static final int IP_PARTS_COUNT = 4;
    private static final int SYMBOLS_COUNT = 16;

    public long getCount(String filePath) throws FileWithIpsNotFoundException, IOException {
        BitSet[] bs = new BitSet[4];
        for (int i = 0; i < 4; ++i) {
            bs[i] = new BitSet(1 << 30);
        }

        char[] charIp = new char[SYMBOLS_COUNT];
        int[] intIpParts = new int[IP_PARTS_COUNT];

        int[] f = new int[IP_PARTS_COUNT]; //array to use only 6 jun bits for the first part of ip
        Arrays.fill(f, 0xff); //0xff = 0b11111111 = 255 (8 jun bits)
        f[0] = 0x3f; //0x3f = 0b00111111 = 63 (6 jun bits)

        BufferedReader fis;

        try {
            fis = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new FileWithIpsNotFoundException(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        reading:
        while (true) {
            Arrays.fill(charIp, ENDL_N);

            for (int i = 0; ; ++i) {
                int ch = fis.read();
                if (ch == -1) {
                    break reading;
                }

                if (ch == ENDL_N) break;
                charIp[i] = (char) ch;
            }

            Arrays.fill(intIpParts, 0);

            //windows - /n/r, linux = /n
            for (int i = 0, j = 0; charIp[i] != ENDL_N && charIp[i] != ENDL_R; ++i) {
                if (charIp[i] == SEPARATOR) ++j;
                else {
                    intIpParts[j] *= 10;
                    intIpParts[j] += charIp[i] - '0';
                }
            }

            //sen 2 bits - index of bitset
            int bsIndex = (intIpParts[0] >> 6);

            int ip = 0;
            for (int i = 0; i < IP_PARTS_COUNT; ++i) {
                //1 part 8 bits - 4 parts 32 bits
                ip <<= 8;

                //& for first part
                ip |= (f[i] & intIpParts[i]);
            }

            bs[bsIndex].set(ip);
        }

        // long cause 2^32 > Integer.MAX_VALUE
        long count = 0;
        for (BitSet b : bs) {
            count += b.cardinality();
        }

        return count;
    }
}
