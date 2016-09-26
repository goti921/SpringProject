package com.payu.storecard.dao;

import com.payu.storecard.constants.CardConstant;
import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.model.Card;
import com.payu.storecard.model.CardBackUp;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simrandeep.singh on 9/25/16.
 */
public class GenericDataDao extends JdbcDaoSupport {
    private String findCardsForUserWithoutCardTokenQuery = "select * from `user_card` uc join `user` u on uc.user_id = u.id where " +
            " u.merchant_id in (:merchantIds) and u.merchant_user_id = (:merchantUserId) and auth_service = (:authService) " +
            " and uc.status = (:cardStatus)";
    private String findCardsForUserWithCardTokenQuery = findCardsForUserWithoutCardTokenQuery + " and uc.card_token = (:cardToken)";
    private String findBackUpCardsForUserWithCardTokenQuery = findCardsForUserWithCardTokenQuery.replaceFirst("user_card", "user_card_backup");

    public List<Card> findCardsForUserWithoutCardToken(CardDetailDTO cardDetailDTO) {
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        Map paramsMap = new HashMap();
        paramsMap.put("merchantIds", cardDetailDTO.getMerchantIdList());
        paramsMap.put("merchantUserId", cardDetailDTO.getMerchantUserId());
        paramsMap.put("authService", cardDetailDTO.getAuthService());
        paramsMap.put("cardStatus", CardConstant.STATUS_ACTIVE);
        return template.query(findCardsForUserWithoutCardTokenQuery, paramsMap, cardRowMapper);
    }

    public Card findCardsForUserWithCardToken(CardDetailDTO cardDetailDTO) {
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        Map paramsMap = new HashMap();
        paramsMap.put("merchantIds", cardDetailDTO.getMerchantIdList());
        paramsMap.put("merchantUserId", cardDetailDTO.getMerchantUserId());
        paramsMap.put("authService", cardDetailDTO.getAuthService());
        paramsMap.put("cardToken", cardDetailDTO.getCardToken());
        paramsMap.put("cardStatus", CardConstant.STATUS_ACTIVE);
        return (Card) template.query(findCardsForUserWithCardTokenQuery, paramsMap, cardRowMapper).get(0);
    }

    public CardBackUp findBackUpCardForUserWithCardToken(CardDetailDTO cardDetailDTO) {
        Map paramsMap = new HashMap();
        paramsMap.put("merchantIds", cardDetailDTO.getMerchantIdList());
        paramsMap.put("merchantUserId", cardDetailDTO.getMerchantUserId());
        paramsMap.put("authService", cardDetailDTO.getAuthService());
        paramsMap.put("cardToken", cardDetailDTO.getCardToken());
        paramsMap.put("cardStatus", CardConstant.STATUS_ACTIVE);
        NamedParameterJdbcTemplate template =
                new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        return (CardBackUp) template.query(findBackUpCardsForUserWithCardTokenQuery, paramsMap, backUpCardRowMapper).get(0);
    }

    RowMapper cardRowMapper = new RowMapper() {

        @Override
        public Card mapRow(ResultSet rs, int i) throws SQLException {
            Card card = new Card();
            card.setStatus(rs.getInt("status"));
            card.setAddedOn(rs.getDate("addedOn"));
            card.setCardMode(rs.getString("card_mode"));
            card.setCardName(rs.getString("card_name"));
            card.setCardToken(rs.getString("card_token"));
            card.setCardType(rs.getString("card_type"));
            card.setEncryptedCardCvv(rs.getString("encrypted_card_cvv"));
            card.setEncryptedCardExpiryMon(rs.getString("encrypted_card_expiry_mon"));
            card.setEncryptedCardExpiryYr(rs.getString("encrypted_card_expiry_yr"));
            card.setEncryptedCardNo(rs.getString("encrypted_card_no"));
            card.setEncryptedNameonCard(rs.getString("encrypted_name_on_card"));
            card.setId(rs.getInt("id"));
            return card;
        }
    };

    RowMapper backUpCardRowMapper = new RowMapper() {

        @Override
        public CardBackUp mapRow(ResultSet rs, int i) throws SQLException {
            CardBackUp cardBackUp = new CardBackUp();
            cardBackUp.setStatus(rs.getInt("status"));
            cardBackUp.setAddedOn(rs.getDate("addedOn"));
            cardBackUp.setCardMode(rs.getString("card_mode"));
            cardBackUp.setCardName(rs.getString("card_name"));
            cardBackUp.setCardToken(rs.getString("card_token"));
            cardBackUp.setCardType(rs.getString("card_type"));
            cardBackUp.setEncryptedCardCvv(rs.getString("encrypted_card_cvv"));
            cardBackUp.setEncryptedCardExpiryMon(rs.getString("encrypted_card_expiry_mon"));
            cardBackUp.setEncryptedCardExpiryYr(rs.getString("encrypted_card_expiry_yr"));
            cardBackUp.setEncryptedCardNo(rs.getString("encrypted_card_no"));
            cardBackUp.setEncryptedNameonCard(rs.getString("encrypted_name_on_card"));
            cardBackUp.setId(rs.getInt("id"));
            return cardBackUp;
        }
    };

}