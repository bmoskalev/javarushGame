package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Page<Player> getAllPlayers(String name, String title, Race race, Profession profession, Long after,
                               Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                               Integer minLevel, Integer maxLevel, Pageable pageable);
    Optional<Player> findById(long id);

    Player save(Player player);

    void deleteById(long id);

//    Player update(Player player);

}
