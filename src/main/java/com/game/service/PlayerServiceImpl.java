package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Player> getAllPlayers(PlayerOrder playerOrder, Integer pageNumber, Integer pageSize) {
        List<Player> players = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        switch (playerOrder) {
            case ID:
//                players.addAll(playerRepository.findByOrderByIdAsc(pageable));
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
                break;
            case NAME:
//                players.addAll(playerRepository.findByOrderByNameAsc(pageable));
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name"));
                break;
            case EXPERIENCE:
//                players.addAll(playerRepository.findByOrderByExperienceAsc(pageable));
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("experience"));
                break;
            case BIRTHDAY:
//                players.addAll(playerRepository.findByOrderByBirthdayAsc(pageable));
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("birthday"));
                break;
            case LEVEL:
//                players.addAll(playerRepository.findByOrderByLevelAsc(pageable));
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("level"));
                break;
        }
        Page<Player> page = playerRepository.findAll(pageable);
//        players = page.getContent();
        return page;
    }

    @Override
    public Optional<Player> findById(long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player save(Player player) {
        Player _player;
        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        if (player.getBanned() == null) {
            player.setBanned(false);
        }
        _player = playerRepository.save(player);
        return _player;
    }

    @Override
    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

//    @Override
//    public Player update(Player player) {
//        Player _player;
//        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
//        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
//        player.setLevel(level);
//        player.setUntilNextLevel(untilNextLevel);
//        if (player.getBanned() == null) {
//            player.setBanned(false);
//        }
//        _player = playerRepository.save(player);
//        return _player;
//    }

}
