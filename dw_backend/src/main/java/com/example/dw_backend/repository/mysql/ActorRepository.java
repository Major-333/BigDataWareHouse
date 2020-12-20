package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {
}
