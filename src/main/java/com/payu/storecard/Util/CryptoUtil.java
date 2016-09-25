package com.payu.storecard.Util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
public class CryptoUtil {
    public static Map<String, String> encrypt(Map dataMap) {
        Map<String, String> encryptedDataMap = new HashMap();
        for(Object key : dataMap.keySet()) {
            String encryptedValue = encrypt((String) dataMap.get(String.valueOf(key)));
            encryptedDataMap.put(String.valueOf(key), encryptedValue);
        }
        return encryptedDataMap;
    }

    public static Map decrypt(Map dataMap) {
        Map decryptedDataMap = new HashMap();
        for(Object key : dataMap.keySet()) {
            String encryptedValue = decrypt((String) dataMap.get(String.valueOf(key)));
            decryptedDataMap.put(String.valueOf(key), encryptedValue);
        }
        return decryptedDataMap;
    }

    public static String encrypt(String data) {

    }

    public static String decrypt(String data) {

    }

    public static String generateToken(String cardNo, String cardExpiryMon, String cardExpiryYr, String merchantUserId, String merchantKey) {
        String input = StringUtils.join(Arrays.asList(cardNo, cardExpiryMon, cardExpiryYr, merchantUserId, merchantKey), "|");
        //return sha1.hexdigest of input
    }
}
