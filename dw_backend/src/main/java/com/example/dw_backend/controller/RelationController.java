package com.example.dw_backend.controller;

import com.example.dw_backend.dao.neo4j.RelationQuery;
import com.example.dw_backend.model.RelationReturn;
import com.example.dw_backend.service.mysql.ActorService;
import com.example.dw_backend.service.mysql.DirectorService;
import com.example.dw_backend.service.neo4j.RelationService;
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

    final private RelationService relationService = new RelationService(
            new RelationQuery("bolt://localhost:7687", "neo4j", "your_password")
    );

    /**
     * 给出导演，查询合作的演员
     *
     * @param directorName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mysql/director-actor", method = RequestMethod.GET)
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
    @RequestMapping(value = "/mysql/director-director", method = RequestMethod.GET)
    public List<HashMap<String, String>> getDirectorByDirector(String directorName) {
        return this.directorService.parsingGetDirectorList(directorName);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/actor-director", method = RequestMethod.GET)
    public List<HashMap<String, String>> getDirectorByActor(String actorName) {
        return this.actorService.parsingGetDirectorList(actorName);
    }

    @ResponseBody
    @RequestMapping(value = "/mysql/actor-actor", method = RequestMethod.GET)
    public List<HashMap<String, String>> getActorByActor(String actorName) {
        return this.actorService.parsingGetDirectorList(actorName);
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/actor-actor", method = RequestMethod.GET)
    public RelationReturn getActorByActorInNeo4j(String actorName) {
        return this.relationService.generateRelation(actorName, "Actor","Actor");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/director-actor", method = RequestMethod.GET)
    public RelationReturn getActorByDirectorInNeo4j(String directorName) {
        return this.relationService.generateRelation(directorName, "Director","Actor");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/director-director", method = RequestMethod.GET)
    public RelationReturn getDirectorByDirectorInNeo4j(String directorName) {
        return this.relationService.generateRelation(directorName, "Director","Director");
    }

    @ResponseBody
    @RequestMapping(value = "/neo4j/actor-director", method = RequestMethod.GET)
    public RelationReturn getDirectorByActorInNeo4j(String actorName) {
        return this.relationService.generateRelation(actorName, "Actor","Director");
    }
}
