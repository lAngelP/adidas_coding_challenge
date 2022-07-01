package net.langelp.subscriptionapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDto {

    private Long id;
    private Date creationTimestamp;
    private Date updateTimestamp;
    private String title;


}
