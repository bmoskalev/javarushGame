package com.game.entity.validation;

import com.game.entity.Player;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CreatePlayerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player) target;
        if (player.getName() == null || player.getName().isEmpty() || player.getName().length() > 12) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if (player.getTitle() == null || player.getTitle().length() > 30) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if (player.getRace() == null) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if (player.getProfession() == null) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if (player.getBirthday() == null || player.getBirthday().getTime() < 0 || player.getBirthday().getYear() < 100 ||
                player.getBirthday().getYear() > 200) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if (player.getExperience() == null || player.getExperience() < 0 || player.getExperience() > 10000000) {
            errors.reject(String.valueOf(HttpStatus.BAD_REQUEST));
        }

    }
}
