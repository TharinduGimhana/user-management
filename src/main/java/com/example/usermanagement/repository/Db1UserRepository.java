package com.example.usermanagement.repository;

import com.example.usermanagement.entity.Db1User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1UserRepository extends JpaRepository<Db1User, Long> {
}

