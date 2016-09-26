package com.payu.storecard.repository;

import com.payu.storecard.model.Card;
import com.payu.storecard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by simrandeep.singh on 9/26/16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByMerchantIdInAndAuthServiceAndMerchantUserId(Collection<Integer> merchantIds, String authService, String merchantUserId);
}
