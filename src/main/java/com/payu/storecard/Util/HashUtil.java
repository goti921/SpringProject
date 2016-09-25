package com.payu.storecard.Util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by simrandeep.singh on 9/25/16.
 */
public class HashUtil {

    public static String generateToken(String cardNo, String cardExpiryMon, String cardExpiryYr, String merchantUserId, String merchantKey) {
        String input = StringUtils.join(Arrays.asList(cardNo, cardExpiryMon, cardExpiryYr, merchantUserId, merchantKey), "|");
        //return sha1.hexdigest of input
    }

    public static Object getSHA1Hash(String cardNo) {
        return null;
    }


    public static Object getSHA256Hash(String cardNo) {
        return null;
    }
}
