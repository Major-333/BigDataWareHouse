package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.Label;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {
    /**
     * XX标签共有多少电影 返回电影列表
     *
     * @param label
     * @return
     */
    @Query(value = "call find_label_movie(:lab);", nativeQuery = true)
    List<Object> getMovieCount(@Param("lab") String label);
}
