package com.payu.storecard.Util;

import com.payu.storecard.constants.CardConstant;
import com.payu.storecard.enums.CipherMode;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HSMCryptoProcessor is using Network Attached Encryption device to
 * encrypt/decrypt text
 */
public class HSMCryptoProcessor {
    private static Logger logger = Logger.getLogger(HSMCryptoProcessor.class);
    private static String HSM_DATA_KEY = "data";
    private static String HSM_CIPHER_MODE_KEY = "mode";
    private static final String HSM_SERVER_URL = "http://127.0.0.1:9090/crypto6/crypto";

    public static String encrypt(String plainText) throws Exception {
        return getCipherText(plainText, CipherMode.ENCRYPT);
    }

    public static String decrypt(String encryptedText) throws Exception {
        return getCipherText(encryptedText, CipherMode.DECRYPT);
    }

    private static String getCipherText(String text, CipherMode cipherMode) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put(HSM_DATA_KEY, text);
        data.put(HSM_CIPHER_MODE_KEY, cipherMode.getName());
        data.put(CardConstant.DONT_LOG, Boolean.TRUE.toString());

        List<String> response = CurlHandler.sendPostRequest(HSM_SERVER_URL, data);
        if (CollectionUtils.isEmpty(response)) {
            throw new Exception("Failed to " + cipherMode);
        }

        String cipherText = getDataFromJson(response.get(0));
        return cipherText;
    }

    private static String getDataFromJson(String jsonStr) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        return jsonObject.getString(HSM_DATA_KEY);
    }
}
