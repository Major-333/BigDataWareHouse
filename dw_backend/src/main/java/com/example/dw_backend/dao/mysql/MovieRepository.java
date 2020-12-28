package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query(value = "call find_movie_by_score(:sco, :larger);", nativeQuery = true)
    List<Movie> getMovieCountByScore(@Param("sco") int score, @Param("larger") String comparison);

    @Query(value = "call find_movie_by_emotion_score(:sco, :larger);", nativeQuery = true)
    List<Movie> getMovieCountByEmotion(@Param("sco") int score, @Param("larger") String comparison);
}
