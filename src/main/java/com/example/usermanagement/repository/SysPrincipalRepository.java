package com.example.usermanagement.repository;

import com.example.usermanagement.entity.SysPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPrincipalRepository extends JpaRepository<SysPrincipal, Long> {
}

