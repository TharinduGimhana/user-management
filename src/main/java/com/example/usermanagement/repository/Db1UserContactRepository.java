package com.example.usermanagement.repository;

import com.example.usermanagement.entity.Db1UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1UserContactRepository extends JpaRepository<Db1UserContact, Long> {
}

