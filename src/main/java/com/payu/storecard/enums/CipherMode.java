package com.payu.storecard.enums;

/**
 * Created by mukesh.kumar1 on 20/05/16.
 */
public enum CipherMode {
    ENCRYPT("encrypt"),
    DECRYPT("decrypt");

    String name;
    CipherMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
