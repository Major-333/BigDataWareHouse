package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Director;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuedixuedi
 */
@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {

    @Query(value = "call find_director_movie(:dir);", nativeQuery = true)
    List<Object> getMovieCount(@Param("dir") String director);

}
