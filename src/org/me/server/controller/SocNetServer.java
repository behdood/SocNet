package org.me.server.controller;


import org.me.server.model.bl_old.UserBL;
import org.me.server.model.bl_old.UserIX;
import org.me.server.model.databases.Database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class SocNetServer extends Thread {

    private Socket clientSocket = null;
    private UserIX userIX;


    public static int nClients = 0;

    private String client_name = "";

    public SocNetServer(Socket clientSocket, Database db) {
        super("SocNetServer");
        this.clientSocket = clientSocket;
        this.userIX = new UserBL(db);
    }

    @Override
    public void run() {

        int client_no;
        PrintWriter outToClients;
        BufferedReader inFromClient;
        try {
            nClients++;
            client_no = nClients;
            System.out.println("Established connection for client #" + client_no);


            outToClients = new PrintWriter(clientSocket.getOutputStream(), true);
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            // Initiate conversation with the client
            String inputLine;
            outToClients.println("Welcome to my social network. What do you want to do? (register / login)");

            while ((inputLine = inFromClient.readLine()) != null) {


                String msg_to_client = processCommand(inputLine);

                if (msg_to_client.equals("exit")) {
                    outToClients.println("Goodbye!");
                    outToClients.flush();
                    break;
                }

                outToClients.println(msg_to_client);
                outToClients.flush();
            }

            // Finish conversation with the client
            System.out.println("Connection closed for client #" + client_no);

        } catch (Exception e) {
            System.out.println("cannot open new input/output stream in server!");
        }


    }
//
//    private void log(String user, String action, UserIX.ActionResult result) {
//        System.out.println("LOG : " + user + " , " + action + " : " + result);
//    }

    private String generateTime() {
        return new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss").
                format(Calendar.getInstance().getTime());
    }

    private String processCommand(String input_line) {
        String[] parsed_cmd = input_line.split(" ", 2);
        String cmd = parsed_cmd[0];
        String[] args;
        String time_stamp, post_id;

        Command c = new Command(cmd, UserIX.ActionResult.UNKNOWN_ERROR);
        switch (cmd) {
            case "register":
                args = parsed_cmd[1].split(" ", 2);
                if (args.length != 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.register(args[0], args[1]));
                break;
            case "login":
                args = parsed_cmd[1].split(" ", 2);
                if (args.length != 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.login(args[0], args[1]));
                if (c.getResult().equals(UserIX.ActionResult.OK)) {
                    client_name = args[0];
                }
                break;
            case "logout":
                if (parsed_cmd.length > 1)
                    return "Too Many Input Arguments for " + cmd;
                c.setResult(userIX.logout());
                if (c.getResult().equals(UserIX.ActionResult.OK) && !client_name.equals(""))
                    client_name = "";
                break;
            case "exit":
                if (parsed_cmd.length > 1)
                    return "Too Many Input Arguments for " + cmd;
                return "exit";

            case "addpost":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                time_stamp = generateTime();
                post_id = time_stamp + "-" + client_name;
                c.setResult(userIX.updateStatus(client_name, parsed_cmd[1], time_stamp, post_id));
                break;
            case "sharepost" :
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                time_stamp = generateTime();
                post_id = time_stamp + "-" + client_name;
                c.setResult(userIX.sharePublic(client_name, parsed_cmd[1], time_stamp, post_id));
                break;
            case "deletepost":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.deleteStatus(client_name, parsed_cmd[1] + "-" + client_name));
                break;

            case "like":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.like(client_name, parsed_cmd[1]));
                break;
            case "unlike":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.unlike(client_name, parsed_cmd[1]));
                break;
            case "follow":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.follow(client_name, parsed_cmd[1]));
                break;
            case "unfollow":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                c.setResult(userIX.unfollow(client_name, parsed_cmd[1]));
                break;

            case "getall":
                if (parsed_cmd.length > 1)
                    return "Too Many Input Arguments for " + cmd;
                return generateStrFromStrIter(userIX.getAllUsers(client_name));
            case "getfriends":
                if (parsed_cmd.length > 1)
                    return "Too Many Input Arguments for " + cmd;
                return generateStrFromStrIter(userIX.getFollowedUsers(client_name));
            case "getfeeds":
                if (parsed_cmd.length > 1)
                    return "Too Many Input Arguments for " + cmd;
                return generateStrFromStrIter(userIX.getFollowedFeeds(client_name));
            case "getlikers":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                return generateStrFromStrIter(userIX.getPostLikers(client_name, parsed_cmd[1]));
            case "getpublic":
                if (parsed_cmd.length < 2)
                    return "Not Enough Arguments for " + cmd;
                return generateStrFromStrIter(userIX.getPublicFeeds(parsed_cmd[1]));
            default:
                System.err.println("UNKNOWN COMMAND : " + cmd);
                break;
        }
        return generateResult(c);

    }

    private String generateResult(Command c) {
        String s;
        switch (c.getResult()) {
            case OK:
                s = c.getAction() + " Successful!";
                if (c.getAction().equals("login"))
                    s += " You are now logged in as " + client_name;
                break;
            case USERNAME_EXIST_ERROR:
                s = "Someone already has this username!";
                break;
            case USERNAME_NOT_EXIST_ERROR:
                s = "Username does not exist";
                break;
            case INCORRECT_PASSWORD_ERROR:
                s = "Wrong password";
                break;
            case NOT_SIGNED_IN_ERROR:
                s = "Please log in to the system!";
                break;
            case INVALID_POST_ID_ERROR:
                s = "Weird thing happened! post_id already exist!";
                break;
            case POST_DOES_NOT_EXIST_ERROR:
                s = "The post does not exist! It may be deleted!";
                break;
            case ALREADY_FOLLOWING_ERROR:
                s = "You are already following this person!";
                break;
            case ALREADY_NOT_FOLLOWING_ERROR:
                s = "You are already not following this person!";
                break;
            case ALREADY_LIKED_ERROR:
                s = "You already have liked this post!";
                break;
            case ALREADY_NOT_LIKED_ERROR:
                s = "You have not liked this post before!";
                break;
            case DATABASE_CONNECTION_ERROR:
                s = "Unable to connect to database!";
                break;
            case UNKNOWN_ERROR:
                s = "UNKNOWN ERROR! WE ARE SORRY!";
                break;
            default:
                s = "UNKNOWN ACTION RESULT! WE ARE SORRY!";
        }
        return s;
    }

    private String generateStrFromStrIter(Iterator<String> sIter){
        if (sIter == null)
            return "";
        String s = "";
        while (sIter.hasNext()) {
            s += sIter.next() + "\t";
        }
        return s;
    }

    private class Command {
        String action;
        UserIX.ActionResult result;

        public Command(String action, UserIX.ActionResult result) {
            this.action = action;
            this.result = result;
        }

        public String getAction() {
            return action;
        }

        public UserIX.ActionResult getResult() {
            return result;
        }

        public void setResult(UserIX.ActionResult result) {
            this.result = result;
        }
    }
}

