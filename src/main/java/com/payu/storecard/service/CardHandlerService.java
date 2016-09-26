package com.payu.storecard.service;

import com.payu.storecard.Util.CryptoUtil;
import com.payu.storecard.Util.HashUtil;
import com.payu.storecard.Util.StoreCardUtil;
import com.payu.storecard.constants.CardConstant;
import com.payu.storecard.dao.GenericDataDao;
import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.dto.ResultDTO;
import com.payu.storecard.model.Card;
import com.payu.storecard.model.CardBackUp;
import com.payu.storecard.model.User;
import com.payu.storecard.repository.CardRepository;
import com.payu.storecard.repository.UserRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by simrandeep.singh on 9/24/16.
 */
@Service
public class CardHandlerService {

    private String CARD_NO = "card_no";
    private String CARD_EXPIRY_MON = "card_expiry_mon";
    private String CARD_EXPIRY_YR = "card_expiry_yr";

    @Autowired
    private GenericDataDao genericDataDao;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;

    public ResultDTO getCardDetails(CardDetailDTO cardDetailDTO) {
        JSONObject resultJson = new JSONObject();
        JSONObject merchantKeysJson = (null != cardDetailDTO.getMerchantKeys()) ? new JSONObject(cardDetailDTO.getMerchantKeys()) :
                null;
        cardDetailDTO.setMerchantIdList(Arrays.asList(cardDetailDTO.getMerchantId().split("\\s*,\\s*")));
        String cardToken = this.getCardToken(cardDetailDTO);
        if(null != cardToken) {
            //Directly fetch card details corresponding to the token
            Card card = genericDataDao.findCardsForUserWithCardToken(cardDetailDTO);
            if(null != card) {
                //Just return
                return new ResultDTO(ResultDTO.SUCCESS, "Card found for given card token", new JSONObject(card));
            } else {
                //Look up for card in back up table
                card = genericDataDao.findBackUpCardForUserWithCardToken(cardDetailDTO);
                if(null != card) {
                    if(null != cardDetailDTO.getCardCvvMerchant()) {
                        User user = userRepository.findByMerchantIdInAndAuthServiceAndMerchantUserId(cardDetailDTO.getMerchantIdList(),
                                cardDetailDTO.getAuthService(), cardDetailDTO.getMerchantUserId()).get(0);
                        String userMerchantKey = (String) merchantKeysJson.getJSONObject(String.valueOf(user.getMerchantId())).get("key");
                        cardDetailDTO.setMerchantKey(userMerchantKey);
                        cardDetailDTO.setCardToken(this.getCardToken(cardDetailDTO));
                        Card newCard = genericDataDao.findCardsForUserWithCardToken(cardDetailDTO);
                        if (null != newCard) {
                            return new ResultDTO(ResultDTO.SUCCESS, "Card found", resultJson.put("card", newCard));
                        }
                    }
                    return new ResultDTO(ResultDTO.SUCCESS, "Card found", resultJson.put("card", card));
                } else {
                    return new ResultDTO(ResultDTO.FAILURE, "Card not found");
                }
            }
        } else {
            //Fetch all card details for the given non-null parameters
            List<Card> userCardList = genericDataDao.findCardsForUserWithoutCardToken(cardDetailDTO);
            if(userCardList.isEmpty()) {
                //return failure : No card found
                return new ResultDTO(ResultDTO.FAILURE, "Card not found");
            } else {
                //extract details from list and return
                return new ResultDTO(ResultDTO.SUCCESS, "Card details found",
                        resultJson.put("card", this.extractCardDetails(userCardList, cardDetailDTO)));
            }
        }

    }

    public ResultDTO deleteCardDetails(CardDetailDTO cardDetailDTO) {
        cardDetailDTO.setMerchantIdList(Arrays.asList(cardDetailDTO.getMerchantId().split("\\s*,\\s*")));
        JSONObject merchantKeysJson = (null != cardDetailDTO.getMerchantKeys()) ? new JSONObject(cardDetailDTO.getMerchantKeys()) :
                null;
        Card card = genericDataDao.findCardsForUserWithCardToken(cardDetailDTO);
        if(null != card) {
            card.setStatus(CardConstant.STATUS_DELETED);
            card.setEncryptedCardCvv(null);
            card = this.addOrUpdate(card);
        } else {
            //Look up for card in back up table
            card = genericDataDao.findBackUpCardForUserWithCardToken(cardDetailDTO);
            if(null != card) {
                card.setStatus(CardConstant.STATUS_DELETED);
                card.setEncryptedCardCvv(null);
                card = this.addOrUpdate(card);
                User user = userRepository.findByMerchantIdInAndAuthServiceAndMerchantUserId(cardDetailDTO.getMerchantIdList(),
                        cardDetailDTO.getAuthService(), cardDetailDTO.getMerchantUserId()).get(0);
                String userMerchantKey = (String) merchantKeysJson.getJSONObject(String.valueOf(user.getMerchantId())).get("key");
                cardDetailDTO.setMerchantKey(userMerchantKey);
                cardDetailDTO.setCardToken(this.getCardToken(cardDetailDTO));
                card = genericDataDao.findCardsForUserWithCardToken(cardDetailDTO);
                if(null != card) {
                    card.setStatus(CardConstant.STATUS_DELETED);
                    card.setEncryptedCardCvv(null);
                    this.addOrUpdate(card);
                }
            } else {
                return new ResultDTO(ResultDTO.FAILURE, "No card found");
            }
        }
        return new ResultDTO(ResultDTO.SUCCESS, "Card deleted", new JSONObject(card.getCardName()));
    }

    private List<Map> extractCardDetails(List<Card> userCardList, CardDetailDTO cardDetailDTO) {
        List<Map> userCards = new ArrayList<>();
        for(Card card : userCardList) {
            Map userCardMap = new HashMap();
            userCardMap.put(CardConstant.CARD_TOKEN_KEY, card.getCardToken());
            userCardMap.put(CardConstant.CARD_NAME_KEY, card.getCardName());
            userCardMap.put(CardConstant.CARD_MODE_KEY, card.getCardMode());
            userCardMap.put(CardConstant.NAME_ON_CARD_KEY, card.getNameOnCard());
            userCardMap.put(CardConstant.CARD_TYPE_KEY, card.getCardType());
            userCardMap.put(CardConstant.CARD_NO_KEY, card.getCardNo());
            userCardMap.put(CardConstant.IS_EXPIRED_KEY, BooleanUtils.toInteger(StoreCardUtil.isCardExpired(card.getCardExpiryMon(), card.getCardExpiryYr())));
            userCardMap.put(CardConstant.EXPIRY_YEAR_KEY, card.getCardExpiryYr());
            userCardMap.put(CardConstant.EXPIRY_MONTH_KEY, card.getCardExpiryMon());
            userCardMap.put(CardConstant.CARD_CVV_KEY, BooleanUtils.toInteger(StringUtils.isNotBlank(card.getEncryptedCardCvv())));
            if(StringUtils.isNotEmpty(cardDetailDTO.getHashes())) {
                userCardMap.put(CardConstant.CARD_NUM_SHA1_KEY, HashUtil.getSHA1Hash(card.getCardNo()));
                userCardMap.put(CardConstant.CARD_NUM_SHA2_KEY, HashUtil.getSHA256Hash(card.getCardNo()));
            }
            userCards.add(userCardMap);
        }
        return userCards;
    }

    private String getCardToken(CardDetailDTO cardDetailDTO) {
        if(StringUtils.isNotBlank(cardDetailDTO.getCardNo()) && StringUtils.isNotBlank(cardDetailDTO.getCardExpYr())
                && StringUtils.isNotBlank(cardDetailDTO.getCardExpMon()) && StringUtils.isNotBlank(cardDetailDTO.getMerchantKey())
                && StringUtils.isNotBlank(cardDetailDTO.getIsEmptyCardTokenFlow())) {
            Map<String, String> dataMap = new HashMap();
            dataMap.put(CARD_NO, cardDetailDTO.getCardNo());
            dataMap.put(CARD_EXPIRY_MON, cardDetailDTO.getCardExpMon());
            dataMap.put(CARD_EXPIRY_YR, cardDetailDTO.getCardExpYr());
            Map<String, String> encryptedDataMap = CryptoUtil.encrypt(dataMap);
            String cardToken = HashUtil.generateToken(encryptedDataMap.get(CARD_NO), encryptedDataMap.get(CARD_EXPIRY_MON),
                    encryptedDataMap.get(CARD_EXPIRY_YR), cardDetailDTO.getMerchantUserId(), cardDetailDTO.getMerchantKey());
            return cardToken;
        }
        return null;
    }

    private Card addOrUpdate(Card card) {
        return cardRepository.save(card);
    }

    private CardBackUp addOrUpdate(CardBackUp card) {
        return cardRepository.save(card);
    }

}
