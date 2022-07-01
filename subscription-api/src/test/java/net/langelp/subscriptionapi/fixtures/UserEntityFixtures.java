package net.langelp.subscriptionapi.fixtures;

import net.langelp.subscriptionapi.repository.entity.UserEntity;

import java.util.Date;

public class UserEntityFixtures {
    public static UserEntity someValidEntity(String email) {
        var entity = new UserEntity();
        entity.setEmail(email);
        entity.setBirthDate(new Date());
        entity.setCreationTimestamp(new Date());
        entity.setUpdateTimestamp(new Date());
        entity.setRgpdConsent(true);
        return entity;
    }
}
