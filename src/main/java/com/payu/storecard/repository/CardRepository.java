package com.payu.storecard.repository;

import com.payu.storecard.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by simrandeep.singh on 9/26/16.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
