package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.DirectorRepository;
import com.example.dw_backend.model.mysql.Director;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    /**
     * 把查询返回值装入键值对list
     *
     * @param directorName
     * @return
     */
    public List<HashMap<String, String>> parsingDirectorMovie(String directorName) {
        List<HashMap<String, String>> result = new ArrayList<>();
        List<Object> direMovieCount = directorRepository.getMovieCount(directorName);
        for (Object row : direMovieCount) {
            Object[] cells = (Object[]) row;
            HashMap<String, String> temp1 = new HashMap<>();
            temp1.put("directorName", String.valueOf(cells[0]));
            temp1.put("movieCount", String.valueOf(cells[1]));
            temp1.put("productId", String.valueOf(cells[2]));
            temp1.put("title", String.valueOf(cells[3]));
            temp1.put("emotionScore", String.valueOf(cells[4]));
            result.add(temp1);
        }
        return result;
    }


    public List<Director> parsingDirectorCount(String count) {
        return this.directorRepository.findDirectorByMovieCount(count);
    }


}
