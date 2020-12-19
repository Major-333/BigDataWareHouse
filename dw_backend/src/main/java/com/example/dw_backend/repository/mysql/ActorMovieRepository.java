package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.ActorMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorMovieRepository extends JpaRepository<ActorMovie,Long> {
}
