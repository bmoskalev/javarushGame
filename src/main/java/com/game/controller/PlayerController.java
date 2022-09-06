package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.entity.validation.CreatePlayerValidator;
import com.game.entity.validation.UpdatePlayerValidator;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/rest")
public class PlayerController {
    final
    PlayerService playerService;

//    @Autowired
//    @Qualifier("createPlayerValidator")
//    private CreatePlayerValidator createPlayerValidator;
//    @Qualifier("updatePlayerValidator")
//    private UpdatePlayerValidator updatePlayerValidator;
//
//    @InitBinder("create")
//    protected void initCreateBinder(WebDataBinder binder) {
//        binder.setValidator(createPlayerValidator);
//    }
//
//    @InitBinder("update")
//    protected void initUpdateBinder(WebDataBinder binder) {
//        binder.setValidator(createPlayerValidator);
//    }

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String title,
                                                      @RequestParam(required = false) Race race,
                                                      @RequestParam(required = false) Profession profession,
                                                      @RequestParam(required = false) Long after,
                                                      @RequestParam(required = false) Long before,
                                                      @RequestParam(required = false) Boolean banned,
                                                      @RequestParam(required = false) Integer minExperience,
                                                      @RequestParam(required = false) Integer maxExperience,
                                                      @RequestParam(required = false) Integer minLevel,
                                                      @RequestParam(required = false) Integer maxLevel,
                                                      @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                      @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        try {
            Page<Player> players;
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
            switch (order) {
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
            players = playerService.getAllPlayers(name, title, race, profession, after, before, banned, minExperience,
                    maxExperience,  minLevel, maxLevel, pageable);
//            if (players.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
            return new ResponseEntity<>(players.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/count")
    public ResponseEntity<Long> getAllPlayersCount(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(required = false) Race race,
                                                   @RequestParam(required = false) Profession profession,
                                                   @RequestParam(required = false) Long after,
                                                   @RequestParam(required = false) Long before,
                                                   @RequestParam(required = false) Boolean banned,
                                                   @RequestParam(required = false) Integer minExperience,
                                                   @RequestParam(required = false) Integer maxExperience,
                                                   @RequestParam(required = false) Integer minLevel,
                                                   @RequestParam(required = false) Integer maxLevel) {
        try {
            Page<Player> players;
            Pageable pageable = PageRequest.of(0, 3, Sort.by("id"));
            players = playerService.getAllPlayers(name, title, race, profession, after, before, banned, minExperience,
                    maxExperience,  minLevel, maxLevel, pageable);
//            if (players.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
            return new ResponseEntity<>(players.getTotalElements(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") long id) {
        try {
            if (id < 1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<com.game.entity.Player> object = playerService.findById(id);
            return object.map(player -> new ResponseEntity<>(player, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/players")
//    public ResponseEntity<Player> createPlayer(@Validated(CreatePlayerValidator.class) @RequestBody Player player) {
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        try {
            if (player.getName() == null || player.getName().isEmpty() || player.getName().length() > 12) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (player.getTitle() == null || player.getTitle().length() > 30) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (player.getRace() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (player.getProfession() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (player.getBirthday() == null || player.getBirthday().getTime() < 0 || player.getBirthday().getYear() < 100 ||
                    player.getBirthday().getYear() > 200) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (player.getExperience() == null || player.getExperience() < 0 || player.getExperience() > 10000000) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Player _player = playerService.save(player);
            return new ResponseEntity<>(_player, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/players/{id}")
//    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @Validated(UpdatePlayerValidator.class) @RequestBody Player player) {
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") long id, @RequestBody Player player) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Player> optionalPlayer = playerService.findById(id);
        if (optionalPlayer.isPresent()) {
            Player oldPlayer = optionalPlayer.get();
            if (player.getName() != null) {
                if (player.getName().isEmpty() || player.getName().length() > 12) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else {
                    oldPlayer.setName(player.getName());
                }
            }
            if (player.getTitle() != null) {
                if (player.getTitle().length() > 30) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else {
                    oldPlayer.setTitle(player.getTitle());
                }
            }
            if (player.getRace() != null) {
                oldPlayer.setRace(player.getRace());
            }
            if (player.getProfession() != null) {
                oldPlayer.setProfession(player.getProfession());
            }
            if (player.getBirthday() != null) {
                if (player.getBirthday().getTime() < 0 || player.getBirthday().getYear() < 100 ||
                        player.getBirthday().getYear() > 200) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else {
                    oldPlayer.setBirthday(player.getBirthday());
                }
            }
            if (player.getBanned() != null) {
                oldPlayer.setBanned(player.getBanned());
            }
            if (player.getExperience() != null) {
                if (player.getExperience() < 0 || player.getExperience() > 10000000) {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                } else {
                    oldPlayer.setExperience(player.getExperience());
                }
            }
            Player newPlayer = playerService.save(oldPlayer);
            return new ResponseEntity<>(newPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable("id") long id) {
        try {
            if (id < 1) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Player> player = playerService.findById(id);
            if (!player.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            playerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
