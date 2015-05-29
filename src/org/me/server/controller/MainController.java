package org.me.server.controller;

import org.me.server.SocNetProtocol;
import org.me.server.model.databases.Database;
import org.me.server.model.databases.FileDatabase;
import org.me.server.model.databases.OracleXeDatabase;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;


public class MainController {

    private static final boolean USE_ORACLE_XE = false;     // if set to false, local storage is used for database!

    public static void main(String[] args) {


        int portNumber = SocNetProtocol.portNo;
        Database db;
        if (USE_ORACLE_XE)
            db = new OracleXeDatabase();
        else
            db = readDatabaseFromDisk();
//        db = FileDatabase.getInstance();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        ServerSocket serverSocket;
        try  {
            if (SocNetProtocol.address.equalsIgnoreCase("localhost"))
                serverSocket = new ServerSocket(portNumber, 0, InetAddress.getByName(null));
            else
                serverSocket = new ServerSocket(portNumber);

            serverSocket.setSoTimeout(1000);
            while (true) {
                try {
                    new SocNetServer(serverSocket.accept(), db).start();
                } catch (IOException ignored) {}

                if (stdIn.ready()){
                    String input_str = stdIn.readLine();
                    if (input_str.equals("exit"))
                        break;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
        finally {
            if (!USE_ORACLE_XE)
                writeDatabaseToDisk(db);

        }
        System.exit(0);
    }

    private static Database readDatabaseFromDisk(){
        try {
            FileInputStream fis = new FileInputStream(new File("server_data.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            Database database = (Database) ois.readObject();
            ois.close();
            fis.close();
            return database;
        } catch (IOException e) {
            System.err.println("Unable to read the database file! Creating a new one ...");
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to read database from disk! Creating a new one ...");
        }
        return FileDatabase.getInstance();
    }

    private static void writeDatabaseToDisk(Database database){
        try {
            FileOutputStream fos = new FileOutputStream(new File("server_data.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(database);
            oos.close();
            fos.close();
            System.out.println("Database saved to disk! exiting the program");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate the database file");
        } catch (IOException e) {
            System.out.println("Unable to save database to disk!");
        }
    }

}
