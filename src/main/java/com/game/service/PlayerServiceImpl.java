package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public Page<Player> getAllPlayers(String name, String title, Race race, Profession profession, Long after,
                                      Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                      Integer minLevel, Integer maxLevel, Pageable pageable) {
//        Page<Player> page = playerRepository.findAll(pageable);
        Page<Player> page = playerRepository
                .findAll((Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (name != null) {
                        predicates.add(cb.like(root.get("name").as(String.class), "%" + name + "%"));
                    }
                    if (title != null) {
                        predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                    }
                    if (race != null) {
                        predicates.add(cb.equal(root.get("race").as(Race.class), race));
                    }
                    if (profession != null) {
                        predicates.add(cb.equal(root.get("profession").as(Profession.class), profession));
                    }
                    if (after != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("birthday").as(Date.class), new Date(after)));
                    }
                    if (before != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("birthday").as(Date.class), new Date(before)));
                    }
                    if (banned != null) {
                        predicates.add(cb.equal(root.get("banned").as(Boolean.class), banned));
                    }
                    if (minExperience != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("experience").as(Integer.class), minExperience));
                    }
                    if (maxExperience != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("experience").as(Integer.class), maxExperience));
                    }
                    if (minLevel != null) {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("level").as(Integer.class), minLevel));
                    }
                    if (maxLevel != null) {
                        predicates.add(cb.lessThanOrEqualTo(root.get("level").as(Integer.class), maxLevel));
                    }
                    return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
                }, pageable);

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
