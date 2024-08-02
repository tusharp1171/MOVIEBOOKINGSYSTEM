package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidRequestException;
import com.example.demo.models.Screen;
import com.example.demo.repository.ScreenRepository;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }

    public Screen getScreenById(Long id) {
        return screenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screen not found with id: " + id));
    }

    public Screen saveScreen(Screen screen) {
        // Example validation, add more as needed
        if (screen.getCapacity() <= 0) {
            throw new InvalidRequestException("Capacity must be greater than zero.");
        }
        return screenRepository.save(screen);
    }

    public void deleteScreen(Long id) {
        if (!screenRepository.existsById(id)) {
            throw new EntityNotFoundException("Screen not found with id: " + id);
        }
        screenRepository.deleteById(id);
    }
}
