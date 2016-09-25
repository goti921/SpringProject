package com.payu.storecard.Util;

import com.payu.storecard.constants.CardConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mukesh.kumar1 on 20/05/16.
 */
public class CurlHandler {
    private static Logger logger = Logger.getLogger(CurlHandler.class);
    public static int DEFAULT_CONNECTION_TIMEOUT = 50000;

    public static List<String> sendPostRequest(String url, Map data) {
        if (!Objects.equals(data.get(CardConstant.DONT_LOG), Boolean.TRUE.toString())) {
            logger.info("Curl request : url : " + url + " data : " + data.toString());
        }

        HttpClient httpclient = getHTTPClient();
        PostMethod postmethod = getPostMethod(url, data);
        List<String> response = new ArrayList<>();

        try {
            //For https
            initializeSSLContext();

            int statusCode = httpclient.executeMethod(postmethod);
            if (statusCode != HttpStatus.SC_NOT_IMPLEMENTED) {
                //Fetch http response
                response = createResponse(postmethod);
            }
        } catch (Exception ex) {
            logger.error("Failed to post Curl. Reason : " + ex.toString());
        } finally {
            postmethod.releaseConnection();
        }

        if (!Objects.equals(data.get(CardConstant.DONT_LOG), Boolean.TRUE.toString())) {
            logger.info("Curl response : " + response.toString());
        }

        return response;
    }

    private static HttpClient getHTTPClient() {
        HttpClient httpclient = new HttpClient();
        httpclient.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
        httpclient.setTimeout(DEFAULT_CONNECTION_TIMEOUT);
        return httpclient;
    }

    private static List<String> createResponse(PostMethod postmethod) throws IOException {
        BufferedReader bufferedreader = new BufferedReader(
                new InputStreamReader(postmethod.getResponseBodyAsStream()));

        String readLine;
        List<String> response = new ArrayList();

        while (((readLine = bufferedreader.readLine()) != null)
                && !StringUtils.isEmpty(readLine)) {
            response.add(readLine);
        }

        return response;
    }

    private static PostMethod getPostMethod(String uri, Map<String, Object> data) {
        PostMethod postmethod = new PostMethod(uri);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            postmethod.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return postmethod;
    }

    private static void initializeSSLContext() throws KeyManagementException, NoSuchAlgorithmException {
        X509TrustManager trustManager = new X509TrustManager() {
            public void checkClientTrusted(final X509Certificate[] xcs, final String string)
                    throws CertificateException {
            }

            public void checkServerTrusted(final X509Certificate[] xcs, final String string)
                    throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { trustManager }, null);
        SSLContext.setDefault(sslContext);
    }
}
