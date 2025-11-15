package com.example.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class AnimalController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final String[] animals = { "cat", "dog", "fox", "mouse", "horse", "bird", "fish", "rabbit" };
    private final Random random = new Random();

    @PostMapping("/api/start")
    public String start() {
        if (isRunning.compareAndSet(false, true)) {
            return "Started";
        }
        return "Already running";
    }

    @PostMapping("/api/stop")
    public String stop() {
        isRunning.set(false);
        return "Stopped";
    }

    @GetMapping("/api/status")
    public String status() {
        return isRunning.get() ? "running" : "stopped";
    }

    @Scheduled(fixedRate = 5000)
    public void sendAnimal() {
        if (isRunning.get()) {
            String animal = animals[random.nextInt(animals.length)];
            messagingTemplate.convertAndSend("/topic/animal", animal);
        }
    }
}