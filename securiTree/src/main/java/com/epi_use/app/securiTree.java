package com.epi_use.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class securiTree {
    private static List<User> userList = new ArrayList<User>();
    private static boolean isLoggedIn = false;
    private static User user;

    public static void main(String[] args) throws IOException {
        login();
        System.out.println("what would you like to do");
        System.out.println("1. display tree");
        System.out.println("2. manage doors");
        System.out.println("3. log out");
        String resp = input();
        switch (resp){
            case "1":
                System.out.println("the tree");
                break;
            case "2":
                System.out.println("the door manager");
                break;
            case "3":
                isLoggedIn = false;
                login();
                break;
            default:
                break;
        }
    }
    public static String input() throws IOException {
        String input;
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        input = reader.readLine();
        return input;
    }
    public static void importUsers(JSONObject userData) {
        Object first_name = userData.get("first_name");
        Object surname = userData.get("surname");
        Object username = userData.get("username");
        Object password = userData.get("password");
        User user = new User(username.toString(), password.toString().hashCode());
        user.setFirst_name(first_name.toString());
        user.setSurname(surname.toString());
        userList.add(user);
    }

    public static void login() {
        Console console = System.console();
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("registered_users.json")) {
            //read the json file
            Object data = jsonParser.parse(reader);
            JSONObject fullData = (JSONObject) data;
            JSONArray users = (JSONArray) fullData.get("registered_users");
            System.out.println(users);
            users.forEach(user -> importUsers((JSONObject) user));
            while(!isLoggedIn) {
                System.out.println("welcome to SecuriTree..");
                System.out.println("Please login");
                System.out.print("Username: ");
                String username = input();
                System.out.print("Password: ");
                char[] passwordChar = console.readPassword();
                String password = new String(passwordChar);
                user = new User(username, password.hashCode());
                userList.forEach(u -> {
                    if (u.getUsername().equals(user.getUsername())) {
                        if (u.getPasswordHash() == (user.getPasswordHash())) {
                            isLoggedIn = true;
                        }
                    }
                });
                if (isLoggedIn) {
                    System.out.println("login successful");
                } else {
                    System.out.println("login failed");
                }

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
