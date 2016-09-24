package com.payu.storecard.service;

import com.payu.storecard.dto.CardDetailDTO;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
@Service
public class CardHandlerService {

    public void getCardDetails(CardDetailDTO cardDetailDTO) {
        JSONObject merchantKeysJson = new JSONObject(cardDetailDTO.getMerchantKeys());
        List merchantIds = Arrays.asList(IndexOutOfBoundsException.cardDetailDTO.getMerchantId().split("\\s*,\\s*"));
    }
}
