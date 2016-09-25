package com.payu.storecard.model;

import com.payu.storecard.Util.CryptoUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
@Table(name = "user_card")
@Entity
public class Card {
    @Column
    @Id
    private Integer id;

    @Column(name = "user_id", columnDefinition = "int(11)")
    private Integer userId;

    @Column(name = "card_name", columnDefinition = "varchar(128)", nullable = false)
    private String cardName;

    @Column(name = "card_mode", columnDefinition = "varchar(10)", nullable = false)
    private String cardMode;

    @Column(name = "card_type", columnDefinition = "varchar(10)", nullable = false)
    private String cardType;

    @Column(name = "encrypted_name_on_card", columnDefinition = "varchar(512)", nullable = false)
    private String encryptedNameonCard;

    @Column(name = "encrypted_card_no", columnDefinition = "varchar(512)", nullable = false)
    private String encryptedCardNo;

    @Column(name = "encrypted_card_expiry_mon", columnDefinition = "varchar(512)", nullable = false)
    private String encryptedCardExpiryMon;

    @Column(name = "encrypted_card_expiry_yr", columnDefinition = "varchar(512)", nullable = false)
    private String encryptedCardExpiryYr;

    @Column(name = "encrypted_card_cvv", columnDefinition = "varchar(512)")
    private String encryptedCardCvv;

    @Column(name = "card_token", columnDefinition = "varchar(512)", nullable = false)
    private String cardToken;

    @Column(name = "status", columnDefinition = "int(11) DEFAULT 0")
    private String status;

    @Column(name = "addedOn", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    private Date addedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardMode() {
        return cardMode;
    }

    public void setCardMode(String cardMode) {
        this.cardMode = cardMode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getEncryptedNameonCard() {
        return encryptedNameonCard;
    }

    public void setEncryptedNameonCard(String encryptedNameonCard) {
        this.encryptedNameonCard = encryptedNameonCard;
    }

    public String getEncryptedCardNo() {
        return encryptedCardNo;
    }

    public void setEncryptedCardNo(String encryptedCardNo) {
        this.encryptedCardNo = encryptedCardNo;
    }

    public String getEncryptedCardExpiryMon() {
        return encryptedCardExpiryMon;
    }

    public void setEncryptedCardExpiryMon(String encryptedCardExpiryMon) {
        this.encryptedCardExpiryMon = encryptedCardExpiryMon;
    }

    public String getEncryptedCardExpiryYr() {
        return encryptedCardExpiryYr;
    }

    public void setEncryptedCardExpiryYr(String encryptedCardExpiryYr) {
        this.encryptedCardExpiryYr = encryptedCardExpiryYr;
    }

    public String getEncryptedCardCvv() {
        return encryptedCardCvv;
    }

    public void setEncryptedCardCvv(String encryptedCardCvv) {
        this.encryptedCardCvv = encryptedCardCvv;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public String getNameOnCard() {
        return CryptoUtil.decrypt(getEncryptedNameonCard());
    }

    public String getCardNo() {
        return CryptoUtil.decrypt(getEncryptedCardNo());
    }

    public String getCardExpiryMon() {
        return CryptoUtil.decrypt(getEncryptedCardExpiryMon());
    }

    public String getCardExpiryYr() {
        return CryptoUtil.decrypt(getEncryptedCardExpiryYr());
    }
}
