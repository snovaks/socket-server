package com.snovaks.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PhoneBookData {

    private static volatile PhoneBookData INSTANCE;

    @Getter
    private Map<String, String> phoneBookMap;

    private PhoneBookData() {
        this.phoneBookMap = new HashMap<>();
    }

    public static PhoneBookData getInstance() {
        if (INSTANCE == null) {
            synchronized (PhoneBookData.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneBookData();
                }
            }
        }
        return INSTANCE;
    }

}