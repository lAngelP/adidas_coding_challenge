package net.langelp.subscriptionapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.langelp.subscriptionapi.controller.dto.CampaignDto;
import net.langelp.subscriptionapi.controller.dto.CreateCampaignRequestDto;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import net.langelp.subscriptionapi.mapper.CampaignMapper;
import net.langelp.subscriptionapi.repository.CampaignRepository;
import net.langelp.subscriptionapi.repository.entity.CampaignEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CampaignService {
    private final CampaignRepository campaignRepository;

    private final ObjectMapper mapper;
    private final CampaignMapper campaignMapper;
    private final KafkaTemplate<String, String> kafkaProducer;

    @Value("${kafka.mailRequestTopic}")
    private String mailRequestTopic;

    public CampaignService(CampaignRepository campaignRepository, ObjectMapper mapper, CampaignMapper campaignMapper, KafkaTemplate<String, String> kafkaProducer) {
        this.campaignRepository = campaignRepository;
        this.mapper = mapper;
        this.campaignMapper = campaignMapper;
        this.kafkaProducer = kafkaProducer;
    }

    public void sendEmailToCampaign(Long campaignId, String mailContent) throws EntityNotFoundException {
        var campaign = this.campaignRepository.findById(campaignId).orElseThrow(() -> new EntityNotFoundException(campaignId.toString()));
        log.info("Sending email to {} users in campaign {}", campaign.getUsers().size(), campaignId);
        campaign.getUsers().forEach(user -> {
            SendEmailRequestEvent contentToSend = SendEmailRequestEvent.builder()
                    .email(user.getEmail())
                    .mailContent(mailContent)
                    .build();
            try {
                kafkaProducer.send(new ProducerRecord<>(mailRequestTopic, mapper.writeValueAsString(contentToSend)));
            } catch (JsonProcessingException e) {
                log.error("Could not request email send to {} in campaign {}", user.getEmail(), campaignId);
            }
        });
        kafkaProducer.flush();
        log.info("Sent email to {} users in campaign {}", campaign.getUsers().size(), campaignId);
    }

    public CampaignDto addCampaign(CreateCampaignRequestDto createCampaignRequestDto) {
        CampaignEntity originalEntity = new CampaignEntity();
        originalEntity.setTitle(createCampaignRequestDto.getTitle());
        CampaignEntity savedEntity = this.campaignRepository.save(originalEntity);
        return this.campaignMapper.mapEntityToDto(savedEntity);
    }

    @Data
    @Builder
    private static class SendEmailRequestEvent{
        private String email;
        private String mailContent;
    }

}
