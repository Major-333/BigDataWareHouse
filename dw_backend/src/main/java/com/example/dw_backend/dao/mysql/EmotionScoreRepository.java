package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.EmotionScore;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmotionScoreRepository extends CrudRepository<EmotionScore, Long> {

    /**
     * 按照情感分数查询电影,输入情感分数以及是否查询大于他的（否则返回小于该分数）
     *
     * @param score
     * @param large
     * @return
     */
    @Query(value = "call find_movie_count_by_emo_score(:sco, :larger);", nativeQuery = true)
    List<Integer> getMovieCount(@Param("sco") int score, @Param("larger") boolean large);
}
