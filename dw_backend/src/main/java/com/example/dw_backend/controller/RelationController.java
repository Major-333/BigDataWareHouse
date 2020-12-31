package com.example.dw_backend.controller;

import com.example.dw_backend.service.mysql.ActorService;
import com.example.dw_backend.service.mysql.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/relationships", produces = {MediaType.APPLICATION_JSON_VALUE})
public class RelationController {
    @Autowired
    private DirectorService directorService;
    @Autowired
    private ActorService actorService;

    /**
     * 给出导演，查询合作的演员
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/director-actor", method = RequestMethod.GET)
    public List<HashMap<String, String>> getActorByDirector(String directorName) {
        return this.directorService.parsingGetActorList(directorName);
    }

    /**
     * 给出导演，查询合作的导演
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/director-director", method = RequestMethod.GET)
    public List<HashMap<String, String>> getDirectorByDirector(String directorName) {
        return this.directorService.parsingGetDirectorList(directorName);
    }

    @ResponseBody
    @RequestMapping(value = "/actor-director", method = RequestMethod.GET)
    public List<HashMap<String, String>> getDirectorByActor(String actorName) {
        return this.actorService.parsingGetDirectorList(actorName);
    }

    @ResponseBody
    @RequestMapping(value = "/actor-actor", method = RequestMethod.GET)
    public List<HashMap<String, String>> getActorByActor(String actorName) {
        return this.actorService.parsingGetDirectorList(actorName);
    }
}
