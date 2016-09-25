package com.payu.storecard.model;

import javax.persistence.*;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"merchant_id", "auth_service", "merchant_user_id"}))
public class User {
    @Id
    @Column
    private Integer id;

    @Column(name = "merchant_id", nullable = false)
    private Integer merchantId;

    @Column(name = "auth_service", columnDefinition = "varchar(20)", nullable = false)
    private String authService;

    @Column(name = "merchant_user_id", columnDefinition = "varchar(256", nullable = false)
    private String merchantUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getAuthService() {
        return authService;
    }

    public void setAuthService(String authService) {
        this.authService = authService;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }
}
