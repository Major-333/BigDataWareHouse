package com.example.dw_backend.dao.mysql;

import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
