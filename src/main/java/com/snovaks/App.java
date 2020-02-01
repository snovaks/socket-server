package com.snovaks;

import com.snovaks.server.Server;

public class App {

    public static void main(String[] args) {

        int argsLength = args.length;

        if (argsLength == 0) {
            System.out.println("*** No arguments passed - running app with default settings ***");
            Server.createDefaultServer();
        } else if (argsLength == 2) {
            System.out.println("*** Host: " + args[0] + " Port: " + args[1] + " ***");
            Server.createCustomServer(args[0], args[1]);
        } else if (argsLength == 3) {
            System.out.println("*** Host: " + args[0] + " Port: " + args[1]
                    + " File: " + args[2] + " ***");
            Server.createCustomServerWithData(args[0], args[1], args[2]);
        } else {
            System.out.println("*** Wrong number of arguments passed ***");
        }

    }

}