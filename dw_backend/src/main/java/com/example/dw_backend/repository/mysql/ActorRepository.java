package com.example.dw_backend.repository.mysql;

import com.example.dw_backend.model.mysql.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {


}
