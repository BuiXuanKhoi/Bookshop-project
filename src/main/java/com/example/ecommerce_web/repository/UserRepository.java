package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.dto.respond.UserRespondDTO;
import com.example.ecommerce_web.model.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

import java.util.List;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserName(String userName);

    @Query(value = "select NEW com.example.ecommerce_web.model.dto.respond.UserRespondDTO( " +
            "usa.userId, usa.userName, inf.email, inf.address, inf.createDate, inf.updateDate," +
            "inf.firstName, inf.lastName,inf.phoneNumber, usa.role, usa.userState) " +
            "from Users usa " +
            "join usa.information inf"
            )
    Page<UserRespondDTO> getPage(Pageable pageable);

    @Query(value = "select urs from Users urs where urs.information.email = :email ", nativeQuery = false)
    Optional<Users> findByEmail(String email);

    @Modifying
    void deleteByUserName(String userName);

}
