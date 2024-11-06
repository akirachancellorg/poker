package com.entjava.poker.service;

import com.entjava.poker.model.PlayerEntity;
import com.entjava.poker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entjava.poker.game.Player;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<PlayerEntity> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<PlayerEntity> getPlayerById(Integer id) {
        return playerRepository.findById(id);
    }

}
