package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class PlayerController {
    final
    PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                      @RequestParam(required = false, defaultValue = "3") int pageSize,
                                                      @RequestParam(required = false, defaultValue = "ID") PlayerOrder order) {
        try {
            List<Player> players;
            players = playerService.getAllPlayers(order);
            if (players.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            int lastId = Math.min(pageSize * (pageNumber + 1), players.size());
            return new ResponseEntity<>(players.subList(pageNumber * pageSize, lastId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> getAllPlayersCount(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                      @RequestParam(required = false, defaultValue = "3") int pageSize,
                                                      @RequestParam(required = false, defaultValue = "ID") PlayerOrder order) {
        try {
            List<Player> players;
            players = playerService.getAllPlayers(order);
            if (players.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(players.size(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") long id) {
        try {
            Optional<com.game.entity.Player> object = playerService.findById(id);
            return object.map(player -> new ResponseEntity<>(player, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            Player _player = playerService.save(player);
            return new ResponseEntity<>(_player, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
