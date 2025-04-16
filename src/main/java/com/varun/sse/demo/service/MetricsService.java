package com.varun.sse.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varun.sse.demo.model.SystemMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MetricsService {

    private final ObjectMapper objectMapper;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final Random random = new Random();

    @Autowired
    public MetricsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    @Scheduled(fixedRate = 1000) // Update every second
    public void simulateMetrics() {
        // Simulate metrics with random fluctuations
        double cpuUsage = Math.min(100, Math.max(0, 50 + random.nextGaussian() * 10)); // 50 ± 10%
        double memoryUsage = Math.min(100, Math.max(0, 70 + random.nextGaussian() * 15)); // 70 ± 15%
        int activeUsers = Math.max(0, 100 + random.nextInt(50) - 25); // 100 ± 25 users

        SystemMetric metrics = new SystemMetric(cpuUsage, memoryUsage, activeUsers);
        System.out.println("metric generation cpu:"+metrics.getCpuUsage()+"memory: "+metrics.getMemoryUsage()+"active users: "+metrics.getActiveUsers());
        broadcast(metrics);
    }

    private void broadcast(SystemMetric metrics) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(objectMapper.writeValueAsString(metrics)));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}