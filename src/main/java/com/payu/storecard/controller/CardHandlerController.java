package com.payu.storecard.controller;

import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.service.CardHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
@Controller
public class CardHandlerController {

    @Autowired
    private CardHandlerService cardHandlerService;

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody void get(
            @RequestParam(name = "merchant_id") String merchantId,
            @RequestParam(name = "merchant_user_id") String merchantUserId,
            @RequestParam(name = "auth_service") String auth_service,
            @RequestParam(name = "hashes") String hashes,
            @RequestParam(name = "card_cvv_merchant") String cardCvvMerchant,
            @RequestParam(name = "merchant_key") String merchantKey,
            @RequestParam(name = "ccnum") String cardNo,
            @RequestParam(name = "ccexpyr") String cardExpYr,
            @RequestParam(name = "ccexpmon") String cardExpMon,
            @RequestParam(name = "emptyCardTokenFlow") String isEmptyCardTokenFlow,
            @RequestParam(name = "merchant_keys") String merchantKeys) {

        CardDetailDTO cardDetailDTO = new CardDetailDTO(merchantId, merchantUserId, auth_service, hashes, cardCvvMerchant, merchantKey,
                cardNo, cardExpYr, cardExpMon, isEmptyCardTokenFlow, merchantKeys);
        cardHandlerService.getCardDetails(cardDetailDTO);

    }
}
