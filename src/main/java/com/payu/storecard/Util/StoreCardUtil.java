package com.payu.storecard.Util;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by simrandeep.singh on 9/25/16.
 */
public class StoreCardUtil {


    public static Boolean isCardExpired(String cardExpiryMon, String cardExpiryYr) {
        if(Integer.valueOf(cardExpiryYr) < Calendar.YEAR || (Integer.valueOf(cardExpiryYr) == Calendar.YEAR
                && Integer.valueOf(cardExpiryMon) < Calendar.MONTH)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
