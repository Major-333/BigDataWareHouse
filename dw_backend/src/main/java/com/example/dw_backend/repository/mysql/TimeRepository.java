package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xuedixuedi
 */
@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

}
