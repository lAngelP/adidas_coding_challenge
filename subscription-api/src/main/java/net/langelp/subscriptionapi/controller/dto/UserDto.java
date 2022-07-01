package net.langelp.subscriptionapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String email;
    private Date creationTimestamp;
    private Date updateTimestamp;
    private String name; //Optional parameter
    private String gender; //Optional parameter
    private Date birthDate;
    private Boolean rgpdConsent;
    Set<Long> campaigns = new HashSet<>();

}
