package com.example.usermanagement.repository;

import com.example.usermanagement.entity.SysSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysSubjectRepository extends JpaRepository<SysSubject, Long> {
}

