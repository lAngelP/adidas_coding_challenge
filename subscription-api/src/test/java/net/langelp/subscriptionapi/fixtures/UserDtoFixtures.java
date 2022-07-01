package net.langelp.subscriptionapi.fixtures;

import net.langelp.subscriptionapi.controller.dto.UserDto;

import java.util.Date;

public class UserDtoFixtures {
    public static UserDto someValidDto(String email) {
        return UserDto.builder()
                .birthDate(new Date())
                .email(email)
                .creationTimestamp(new Date())
                .updateTimestamp(new Date())
                .rgpdConsent(true)
                .build();
    }
}
