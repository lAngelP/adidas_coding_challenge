package net.langelp.subscriptionapi.mapper;

import net.langelp.subscriptionapi.controller.dto.CampaignDto;
import net.langelp.subscriptionapi.repository.entity.CampaignEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampaignMapper {
    CampaignDto mapEntityToDto(CampaignEntity entity);
}
