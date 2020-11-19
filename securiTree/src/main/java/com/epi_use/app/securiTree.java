package com.epi_use.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class securiTree {
    private static List<User> userList = new ArrayList<User>();
    private static List<Area> areaList = new ArrayList<Area>();
    private static List<Door> doorList = new ArrayList<Door>();
    private static boolean isLoggedIn = false;
    private static User user;

    public static void main(String[] args) throws IOException {
        importUsers();
        importSystem();
        login();
        homepage();
    }

    private static void homepage() throws IOException {
        System.out.println("what would you like to do");
        System.out.println("1. display tree");
        System.out.println("2. manage doors");
        System.out.println("3. log out");
        String resp = input();
        switch (resp){
            case "1":
                System.out.println("the tree");
                displayTree();
                System.out.println("press enter to go back to homepage");
                input();
                homepage();
                break;
            case "2":
                System.out.println("the door manager");
                System.out.print("Please enter the door id: ");
                String id = input();
                System.out.print("please enter the new Status: ");
                String status = input();
                manageDoor(id, status);
                System.out.println("press enter to go back to homepage");
                input();
                homepage();
                break;
            case "3":
                isLoggedIn = false;
                login();
                break;
            default:
                break;
        }
    }

    private static void manageDoor(String id, String status) {
        AtomicBoolean found = new AtomicBoolean(false);
        doorList.forEach(door -> {
            if(door.getId().equals(id)){
                found.set(true);
                door.setStatus(status);
            }
        });
        if(found.get()){
            System.out.println(id + " set to "+ status);
        } else {
            System.out.println("door not found");
        }
    }

    private static void displayTree() {

    }

    public static String input() throws IOException {
        String input;
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        input = reader.readLine();
        return input;
    }
    public static void parseUsers(JSONObject userData) {
        Object first_name = userData.get("first_name");
        Object surname = userData.get("surname");
        Object username = userData.get("username");
        Object password = userData.get("password");
        User user = new User(username.toString(), password.toString().hashCode());
        user.setFirst_name(first_name.toString());
        user.setSurname(surname.toString());
        userList.add(user);
    }

    public static void importUsers() {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("registered_users.json")) {
            //read the json file
            Object data = jsonParser.parse(reader);
            JSONObject fullData = (JSONObject) data;
            JSONArray users = (JSONArray) fullData.get("registered_users");
            users.forEach(user -> parseUsers((JSONObject) user));
        } catch (ParseException | IOException e) {
        e.printStackTrace();
        }
    }

    public static void importSystem() {
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("system_data.json")) {
            //read the json file
            Object data = jsonParser.parse(reader);
            JSONObject fullData = (JSONObject) data;
            JSONObject systemData = (JSONObject) fullData.get("system_data");
            JSONArray doors = (JSONArray) systemData.get("doors");
            JSONArray areas = (JSONArray) systemData.get("areas");
            areas.forEach(area -> parseAreas((JSONObject) area));
            doors.forEach(door -> parseDoors((JSONObject) door));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseDoors(JSONObject systemData) {
        Object id = systemData.get("id");
        Object name = systemData.get("name");
        Object parent_area = systemData.get("parent_area");
        Object status = systemData.get("status");
        Door door = new Door(id.toString(), name.toString(), parent_area.toString(), status.toString());
        doorList.add(door);
    }

    private static void parseAreas(JSONObject systemData) {
        Object id = systemData.get("id");
        Object name = systemData.get("name");
        Object parent_area_id = systemData.get("parent_area");
        Object child_area_obj = systemData.get("child_area_ids");
        String[] child_area_ids = child_area_obj.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("]", "").split(",");
        if (parent_area_id == null) {
            Area area = new Area(id.toString(), name.toString(), "root");
            areaList.add(area);
        }else{
            Area area = new Area(id.toString(), name.toString(), parent_area_id.toString());
            areaList.add(area);
        }
    }

    public static void login() throws IOException {
        Console console = System.console();
            while(!isLoggedIn) {
                System.out.println("welcome to SecuriTree..");
                System.out.println("Please login");
                System.out.print("Username: ");
                String username = input();
                System.out.print("Password: ");
                String password;
                if(console == null) {
                    password = input();
                }else {
                    char[] passwordChar = console.readPassword();
                    password = new String(passwordChar);
                }
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
    }
}
