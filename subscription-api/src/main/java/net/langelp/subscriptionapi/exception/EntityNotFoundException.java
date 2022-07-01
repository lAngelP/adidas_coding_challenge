package net.langelp.subscriptionapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityNotFoundException extends Exception{
    private String entityId;
}
