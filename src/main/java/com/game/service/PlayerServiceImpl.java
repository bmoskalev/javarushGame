package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    final
    PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAllPlayers(PlayerOrder playerOrder) {
        List<Player> players = new ArrayList<>();
        switch (playerOrder) {
            case ID:
                players.addAll(playerRepository.findByOrderByIdAsc());
                break;
            case NAME:
                players.addAll(playerRepository.findByOrderByNameAsc());
                break;
            case EXPERIENCE:
                players.addAll(playerRepository.findByOrderByExperienceAsc());
                break;
            case BIRTHDAY:
                players.addAll(playerRepository.findByOrderByBirthdayAsc());
                break;
            case LEVEL:
                players.addAll(playerRepository.findByOrderByLevelAsc());
                break;
        }
        return players;
    }

    @Override
    public Optional<Player> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Player save(Player player) {
        Player _player;
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        _player = playerRepository.save(new Player(player.getName(), player.getTitle(), player.getRace(), player.getProfession(),
                player.getExperience(), player.getLevel(), player.getUntilNextLevel(), player.getBirthday(), player.getBanned()));
        return _player;
    }
}
