package net.langelp.subscriptionapi.service;

import net.langelp.subscriptionapi.controller.dto.CreateUserRequestDto;
import net.langelp.subscriptionapi.controller.dto.UserDto;
import net.langelp.subscriptionapi.exception.EntityAlreadyFoundException;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import net.langelp.subscriptionapi.mapper.UserMapper;
import net.langelp.subscriptionapi.repository.CampaignRepository;
import net.langelp.subscriptionapi.repository.UserRepository;
import net.langelp.subscriptionapi.repository.entity.UserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, CampaignRepository campaignRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.userMapper = userMapper;
    }

    public void addSubscription(String email, Long campaignId) throws EntityNotFoundException {
        var user = this.userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(email));
        var campaign = this.campaignRepository.findById(campaignId).orElseThrow(() -> new EntityNotFoundException(campaignId.toString()));
        if(user.getCampaigns().stream().noneMatch(c -> Objects.equals(campaignId, c.getId()))) {
            user.getCampaigns().add(campaign);
            userRepository.save(user);
        }
    }

    public void cancelSubscription(String email, Long campaignId) throws EntityNotFoundException {
        var user = this.userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(email));
        var campaign = this.campaignRepository.findById(campaignId).orElseThrow(() -> new EntityNotFoundException(campaignId.toString()));
        if(user.getCampaigns().stream().anyMatch(c -> Objects.equals(campaignId, c.getId()))) {
            user.getCampaigns().remove(campaign);
            userRepository.save(user);
        }else{
            throw new EntityNotFoundException(email + " - " + campaignId);
        }
    }

    @Cacheable("subscriptions-paged")
    public List<UserDto> getSubscriptions(Integer page, Integer size) {
        var users = this.userRepository.findAll(Pageable.ofSize(size).withPage(page));
        return this.userMapper.mapEntityToDto(users.getContent());
    }

    @Cacheable("subscriptions")
    public UserDto getSubscription(String email, Long campaignId) throws EntityNotFoundException {
        var user = this.userRepository.findById(email).orElseThrow(() -> new EntityNotFoundException(email));
        var userDto = this.userMapper.mapEntityToDto(user);
        if(userDto.getCampaigns().contains(campaignId)){
            userDto.setCampaigns(Set.of(campaignId));
        }else{
            throw new EntityNotFoundException(email + " - " + campaignId);
        }
        return userDto;
    }

    public UserDto addUser(String email, CreateUserRequestDto createUserRequestDto) throws EntityAlreadyFoundException {
        var user = this.userRepository.findById(email);
        if(user.isPresent()){
            throw new EntityAlreadyFoundException(email);
        }
        UserEntity entity = this.userMapper.mapRequestToEntity(createUserRequestDto);
        entity.setEmail(email);
        entity = this.userRepository.save(entity);
        return this.userMapper.mapEntityToDto(entity);
    }
}
