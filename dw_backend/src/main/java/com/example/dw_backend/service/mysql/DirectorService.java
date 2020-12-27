package com.example.dw_backend.service.mysql;

import com.example.dw_backend.repository.mysql.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Map<String, String>> parsingDirectorMovie(String directorName) {
        List<Map<String, String>> result = new ArrayList<>();
        List<Object> direMovieCount = directorRepository.getMovieCount(directorName);

        for (Object row : direMovieCount) {
            Object[] cells = (Object[]) row;
            Map<String, String> temp1 = new HashMap<>();
            temp1.put("director_name", cells[0].toString());
            temp1.put("movie_count", cells[1].toString());
            temp1.put("product_id", cells[2].toString());
            temp1.put("emotion_score", cells[4].toString());
            result.add(temp1);
        }
        return result;
    }
}
