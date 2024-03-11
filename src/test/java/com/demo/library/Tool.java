package com.demo.library;

import java.util.Date;
import java.util.Random;

public class Tool {
    static Random random = new Random(new Date().getTime());
    static String[] op = {"BB", "RB", "RO", "RL"};
    public static String randomString() {
        return Long.toString(random.nextLong() % 16351621);
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static String randomCordType(){
        return op[random.nextInt(0,4)];
    }
}
