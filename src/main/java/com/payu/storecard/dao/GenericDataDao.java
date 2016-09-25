package com.payu.storecard.dao;

import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.model.Card;
import com.payu.storecard.model.CardBackUp;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by simrandeep.singh on 9/25/16.
 */
public class GenericDataDao extends JdbcDaoSupport {
    private String findCardsForUserWithoutCardTokenQuery = "";
    private String findCardsForUserWithCardTokenQuery = "";

    public List<Card> findCardsForUserWithoutCardToken(CardDetailDTO cardDetailDTO) {
        return getJdbcTemplate().query(findCardsForUserWithoutCardTokenQuery, new Object[]{cardDetailDTO.getMerchantId(),
        cardDetailDTO.getMerchantUserId(), cardDetailDTO.getAuthService()}, cardRowMapper);
    }

    public Card findCardsForUserWithCardToken(CardDetailDTO cardDetailDTO) {
        return (Card) getJdbcTemplate().queryForObject(findCardsForUserWithCardTokenQuery, new Object[]{cardDetailDTO.getMerchantId(),
                cardDetailDTO.getMerchantUserId(), cardDetailDTO.getAuthService()}, cardRowMapper);
    }

    public CardBackUp findBackUpCardForUserWithCardToken(CardDetailDTO cardDetailDTO) {
        return (CardBackUp) getJdbcTemplate().queryForObject(findCardsForUserWithCardTokenQuery, new Object[]{cardDetailDTO.getMerchantId(),
                cardDetailDTO.getMerchantUserId(), cardDetailDTO.getAuthService()}, backUpCardRowMapper);
    }

    RowMapper cardRowMapper = new RowMapper() {

        @Override
        public Card mapRow(ResultSet rs, int i) throws SQLException {
            Card card = new Card();
            card.setStatus(rs.getString("status"));
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
            cardBackUp.setStatus(rs.getString("status"));
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