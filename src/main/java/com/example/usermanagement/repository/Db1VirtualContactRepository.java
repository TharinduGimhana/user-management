package com.example.usermanagement.repository;

import com.example.usermanagement.entity.Db1VirtualContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Db1VirtualContactRepository extends JpaRepository<Db1VirtualContact, Long> {
}

