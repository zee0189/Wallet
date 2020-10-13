package com.project.wallet.dao;

import com.project.wallet.model.WalletUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<WalletUser, String> {

  @Query("select u from WalletUser u where u.username = username")
   Optional<WalletUser> getWalletUserByUsername(@Param("username") String username);

}
