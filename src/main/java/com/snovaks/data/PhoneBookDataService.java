package com.snovaks.data;

import java.io.*;

public class PhoneBookDataService {

    PhoneBookData phoneBookData;

    public PhoneBookDataService() {
        phoneBookData = PhoneBookData.getInstance();
    }

    public void loadDataFromTheFile(String fileName) {
        if (fileName == null) {
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(" +", 2);
                phoneBookData.getPhoneBookMap().put(data[0], data[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPhoneNumber(String name) {
        return phoneBookData.getPhoneBookMap().get(name);
    }

    public boolean addPhoneNumber(String name, String number) {
        if (phoneBookData.getPhoneBookMap().containsKey(name)) {
            return false;
        }
        phoneBookData.getPhoneBookMap().put(name, number);
        return true;
    }

    public boolean replacePhoneNumber(String name, String number) {
        if (!phoneBookData.getPhoneBookMap().containsKey(name)) {
            return false;
        }
        phoneBookData.getPhoneBookMap().put(name, number);
        return true;
    }

}