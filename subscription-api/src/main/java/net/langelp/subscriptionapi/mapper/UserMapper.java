package net.langelp.subscriptionapi.mapper;

import net.langelp.subscriptionapi.controller.dto.CreateUserRequestDto;
import net.langelp.subscriptionapi.controller.dto.UserDto;
import net.langelp.subscriptionapi.repository.entity.CampaignEntity;
import net.langelp.subscriptionapi.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    List<UserDto> mapEntityToDto(List<UserEntity> entity);
    UserDto mapEntityToDto(UserEntity entity);

    default Long mapCampaignId(CampaignEntity entity){
        return entity.getId();
    }

    //@Mapping(source = "rgpdConsent", target = "rgpdConsent")
    @Mapping(source = "birthDate", target = "birthDate")
    UserEntity mapRequestToEntity(CreateUserRequestDto createUserRequestDto);
}
