package com.payu.storecard.controller;

import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.dto.ResultDTO;
import com.payu.storecard.service.CardHandlerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody
    ResponseEntity get(
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
                cardNo, cardExpYr, cardExpMon, isEmptyCardTokenFlow, merchantKeys, null);
        ResultDTO resultDTO = cardHandlerService.getCardDetails(cardDetailDTO);
        JSONObject resultJson = this.createFinalResponse(resultDTO)
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    ResponseEntity delete(
            @RequestParam(name = "merchant_id") String merchantId,
            @RequestParam(name = "merchant_user_id") String merchantUserId,
            @RequestParam(name = "auth_service") String auth_service,
            @RequestParam(name = "merchant_key") String merchantKey,
            @RequestParam(name = "merchant_keys") String merchantKeys,
            @RequestParam(name = "card_token") String cardToken) {
        CardDetailDTO cardDetailDTO = new CardDetailDTO(merchantId, merchantUserId, auth_service, null, null, merchantKey, null, null,
                null, null, merchantKeys, cardToken);
        ResultDTO resultDTO = cardHandlerService.deleteCardDetails(cardDetailDTO);
        JSONObject resultJson = this.createFinalResponse(resultDTO);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

    private JSONObject createFinalResponse(ResultDTO resultDTO) {
        JSONObject resultJson = (JSONObject) resultDTO.getResult();
        if(ResultDTO.SUCCESS == resultDTO.getStatus()) {
            resultJson = (JSONObject) resultDTO.getResult();
            resultJson.put("status", "SUCCESS");
        } else {
            resultJson = new JSONObject();
            resultJson.put("status", "FAILURE");
            resultJson.put("error", "Card not found.");
        }
        return resultJson;
    }
}
