package com.epam.jwd;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println(func(4));
    }

    public static int func(int x) {
        int res = x;
        if(x > 1){
            res *= func(x - 1);
        }else {
            return 1;
        }
        return res;
    }
}
