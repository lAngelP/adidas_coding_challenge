package net.langelp.subscriptionapi.controller;

import net.langelp.subscriptionapi.controller.dto.CampaignDto;
import net.langelp.subscriptionapi.controller.dto.CreateCampaignRequestDto;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import net.langelp.subscriptionapi.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    private void checkString(String str){
        if(Objects.isNull(str) || str.isEmpty()){
            throw new IllegalArgumentException("Null or empty");
        }
    }

    @PutMapping("/{campaignId}/email")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void cancelSubscription(@PathVariable Long campaignId, @RequestBody String mailContent) throws EntityNotFoundException {
        checkString(mailContent);
        this.campaignService.sendEmailToCampaign(campaignId, mailContent);
    }

    @PostMapping
    public CampaignDto addUser(@RequestBody CreateCampaignRequestDto createCampaignRequestDto) {
        checkString(createCampaignRequestDto.getTitle());
        return this.campaignService.addCampaign(createCampaignRequestDto);
    }


}
