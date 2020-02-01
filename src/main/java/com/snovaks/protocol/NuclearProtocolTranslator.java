package com.snovaks.protocol;

public class NuclearProtocolTranslator {

    public static int getCode(NuclearProtocol nuclearProtocol) {
        switch (nuclearProtocol) {
            case BYE:
                return 0;
            case BAD_REQUEST:
                return 1;
            case GET:
                return 2;
            case ADD:
                return 3;
            case REPLACE:
                return 4;
            case NOT_FOUND:
                return 5;
            case OK:
                return 6;
            default:
                return 1;
        }
    }

    public static NuclearProtocol translateFromString(String request) {
        if (request.equals("bye")) {
            return NuclearProtocol.BYE;
        } else if (request.equals("get")) {
            return NuclearProtocol.GET;
        } else if (request.equals("add")) {
            return NuclearProtocol.ADD;
        } else if (request.equals("replace")) {
            return NuclearProtocol.REPLACE;
        } else if (request.equals("not found")) {
            return NuclearProtocol.NOT_FOUND;
        } else if (request.equals("ok")) {
            return NuclearProtocol.OK;
        }
        return NuclearProtocol.BAD_REQUEST;
    }

    public static String getProtocolMethodName(int code) {
        if (code == 0)
            return "bye";
        else if (code == 1)
            return "bad request";
        else if (code == 2)
            return "get";
        else if (code == 3)
            return "add";
        else if (code == 4)
            return "replace";
        else if (code == 5)
            return "not found";
        else if (code == 6)
            return "ok";
        else
            return "bad request";
    }

}