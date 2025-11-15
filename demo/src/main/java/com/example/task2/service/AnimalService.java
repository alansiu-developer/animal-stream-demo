package com.example.task2.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class AnimalService {
    private static final List<String> ANIMALS = List.of("cat", "dog", "mouse", "horse", "fox");
    private final Random random = new Random();

    public String getRandomAnimal() {
        return ANIMALS.get(random.nextInt(ANIMALS.size()));
    }
}