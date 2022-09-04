package com.example.ecommerce_web.repository;

import com.example.ecommerce_web.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


    @Query(value = "select * from roles where role_name = :roleName", nativeQuery = true)
    Role getRoleByRoleName(String roleName);
}
