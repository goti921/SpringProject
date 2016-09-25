package com.payu.storecard.dto;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
public class CardDetailDTO {
    private String merchantId;
    private String merchantUserId;
    private String authService;
    private String hashes;
    private String cardCvvMerchant;
    private String merchantKey;
    private String cardNo;
    private String cardExpYr;
    private String cardExpMon;
    private String isEmptyCardTokenFlow;
    private String merchantKeys;

    public CardDetailDTO(String merchantId, String merchantUserId, String auth_service, String hashes, String cardCvvMerchant, String merchantKey, String cardNo, String cardExpYr, String cardExpMon, String isEmptyCardTokenFlow, String merchantKeys) {
        this.merchantId = merchantId;
        this.merchantUserId = merchantUserId;
        this.authService = auth_service;
        this.hashes = hashes;
        this.cardCvvMerchant = cardCvvMerchant;
        this.merchantKey = merchantKey;
        this.cardNo = cardNo;
        this.cardExpYr = cardExpYr;
        this.cardExpMon = cardExpMon;
        this.isEmptyCardTokenFlow = isEmptyCardTokenFlow;
        this.merchantKeys = merchantKeys;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public String getAuthService() {
        return authService;
    }

    public void setAuthService(String authService) {
        this.authService = authService;
    }

    public String getHashes() {
        return hashes;
    }

    public void setHashes(String hashes) {
        this.hashes = hashes;
    }

    public String getCardCvvMerchant() {
        return cardCvvMerchant;
    }

    public void setCardCvvMerchant(String cardCvvMerchant) {
        this.cardCvvMerchant = cardCvvMerchant;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardExpYr() {
        return cardExpYr;
    }

    public void setCardExpYr(String cardExpYr) {
        this.cardExpYr = cardExpYr;
    }

    public String getCardExpMon() {
        return cardExpMon;
    }

    public void setCardExpMon(String cardExpMon) {
        this.cardExpMon = cardExpMon;
    }

    public String getIsEmptyCardTokenFlow() {
        return isEmptyCardTokenFlow;
    }

    public void setIsEmptyCardTokenFlow(String isEmptyCardTokenFlow) {
        this.isEmptyCardTokenFlow = isEmptyCardTokenFlow;
    }

    public String getMerchantKeys() {
        return merchantKeys;
    }

    public void setMerchantKeys(String merchantKeys) {
        this.merchantKeys = merchantKeys;
    }
}
