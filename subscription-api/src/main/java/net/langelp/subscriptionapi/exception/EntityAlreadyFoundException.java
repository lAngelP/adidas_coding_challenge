package net.langelp.subscriptionapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityAlreadyFoundException extends Exception {

    private String email;
}
