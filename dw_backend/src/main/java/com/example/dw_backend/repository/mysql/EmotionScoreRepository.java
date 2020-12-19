package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.EmotionScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionScoreRepository extends JpaRepository<EmotionScore,Long>{
}
