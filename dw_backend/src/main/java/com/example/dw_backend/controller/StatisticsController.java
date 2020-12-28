package com.example.dw_backend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistics", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatisticsController {
}
