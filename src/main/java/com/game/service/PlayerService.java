package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    List<Player> getAllPlayers(PlayerOrder playerOrder);

    Optional<Player> findById(long id);

    Player save(Player player);
}
