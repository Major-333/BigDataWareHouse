package com.example.dw_backend.bootstrap;

import com.example.dw_backend.repository.mysql.DirectorRepository;
import com.example.dw_backend.repository.mysql.TimeRepository;
import com.example.dw_backend.service.mysql.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MovieBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DirectorRepository directorRepository;

    public MovieBootstrap(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        long startTime = System.currentTimeMillis();    //获取开始时间

        DirectorService directorService = new DirectorService(directorRepository);
        List<Map<String, String>> result = directorService.parsingDirectorMovie("Aaron Lipstadt");
        List<Object> direMovieCount = directorRepository.getMovieCount("Aaron Lipstadt");

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println(result);
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间
    }
}
