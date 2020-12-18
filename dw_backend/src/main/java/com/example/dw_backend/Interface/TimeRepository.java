package com.example.dw_backend.Interface;

import com.example.dw_backend.Entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
    /**
     * 根据日期找电影？
     * @param DateTime
     * @return
     */
    public Time findByDay(String DateTime);
}
