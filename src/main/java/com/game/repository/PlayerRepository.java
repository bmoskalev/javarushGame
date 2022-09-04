package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByOrderByIdAsc();

    List<Player> findByOrderByNameAsc();

    List<Player> findByOrderByExperienceAsc();

    List<Player> findByOrderByBirthdayAsc();

    List<Player> findByOrderByLevelAsc();
}
