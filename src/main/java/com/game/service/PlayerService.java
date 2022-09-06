package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Page<Player> getAllPlayers(PlayerOrder playerOrder, Integer pageNumber, Integer pageSize);

    Optional<Player> findById(long id);

    Player save(Player player);

    void deleteById(long id);

//    Player update(Player player);

}
