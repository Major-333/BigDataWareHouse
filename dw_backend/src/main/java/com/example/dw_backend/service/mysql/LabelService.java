package com.example.dw_backend.service.mysql;

import com.example.dw_backend.dao.mysql.LabelRepository;
import com.example.dw_backend.model.mysql.Actor;
import com.example.dw_backend.model.mysql.Label;
import com.example.dw_backend.model.mysql.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    /**
     * 把查询返回值变成MovieList类型
     *
     * @param label
     * @return
     */
    public List<Movie> parsingLabelMovie(String label) {
        List<Label> labelList = labelRepository.getMovieCount(label);
        List<Movie> movieList = new ArrayList<>();
        for (Label label1 : labelList) {
            movieList.add(label1.getMovie());
        }
        return movieList;
    }
}
