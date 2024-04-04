package com.denton.shop.clothshop.repository;

import com.denton.shop.clothshop.model.UserAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressesRepository extends JpaRepository<UserAddresses, Long> {
}
