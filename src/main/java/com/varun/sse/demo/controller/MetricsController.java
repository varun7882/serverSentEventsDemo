package com.varun.sse.demo.controller;

import com.varun.sse.demo.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class MetricsController {

    private final MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics-stream")
    public SseEmitter streamMetrics() {
        System.out.println(" hello in emitter");
        return metricsService.subscribe();
    }
}