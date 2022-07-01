package net.langelp.subscriptionapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {

    private Date birthDate;
    private String gender;
    private String name;
    private Boolean rgpdConsent;

}
