package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {
    /**
     * XX分数以上/以下的有多少电影 返回电影数量
     *
     * @param score
     * @param large
     * @return
     */
    @Query(value = "call find_movie_count_by_score(:sco, :larger);", nativeQuery = true)
    List<Integer> getMovieCount(@Param("sco") int score, @Param("larger") boolean large);
}
