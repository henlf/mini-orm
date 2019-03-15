package com.henlf.orm;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println(Main.class.getClass().getResource("/"));
        String total = Main.class.getClass().getResource("/").getPath() + "com/henlf/orm";
        System.out.println(total);
        System.out.println(new File(total).isDirectory());
    }
}
