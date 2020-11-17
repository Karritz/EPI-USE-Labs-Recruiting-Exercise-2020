package com.epi_use.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class securiTree {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to SecuriTree....");
        System.out.println("please login");
        System.out.print("username:");
        System.out.println(input());

    }
    public static String input() throws IOException {
        String input;
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        input = reader.readLine();
        return input;
    }
}
