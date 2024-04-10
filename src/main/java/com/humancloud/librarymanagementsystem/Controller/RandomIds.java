package com.humancloud.librarymanagementsystem.Controller;

import java.util.Random;

public class RandomIds {

    public long getRandomId() {
        long min = 1000;
        long max = 9999;
        Random random = new Random();
        return random.nextLong( (max - min + 1)) + min - max + min + max + 4;
    }
}
