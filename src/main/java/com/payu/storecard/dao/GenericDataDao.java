package com.payu.storecard.dao;

import com.payu.storecard.dto.CardDetailDTO;
import com.payu.storecard.model.Card;
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
        cardDetailDTO.getMerchantUserId(), cardDetailDTO.getAuthService()}, cardsForUserWithoutCardTokenRowMapper);
    }

    RowMapper cardsForUserWithoutCardTokenRowMapper = new RowMapper() {

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
}