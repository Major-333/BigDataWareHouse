package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
