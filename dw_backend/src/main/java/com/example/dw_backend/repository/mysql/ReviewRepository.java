package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Version, Long> {
}
