package net.langelp.subscriptionapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.langelp.subscriptionapi.exception.EntityNotFoundException;
import net.langelp.subscriptionapi.fixtures.CampaignEntityFixtures;
import net.langelp.subscriptionapi.mapper.CampaignMapper;
import net.langelp.subscriptionapi.repository.CampaignRepository;
import net.langelp.subscriptionapi.repository.entity.CampaignEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaProducer;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(campaignService, "mailRequestTopic", "dummyTopic");
    }

    @Test
    public void testEmailIsSentToAllUsersInCampaign() throws EntityNotFoundException {
        CampaignEntity campaignEntity = CampaignEntityFixtures.someValidEntity();
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaignEntity));
        when(kafkaProducer.send(any(ProducerRecord.class))).thenReturn(null);

        campaignService.sendEmailToCampaign(1L, "Test email content");
        verify(kafkaProducer, times(campaignEntity.getUsers().size())).send(any(ProducerRecord.class));
        verify(kafkaProducer, times(1)).flush();

    }

    @Test
    public void testEmailSendingWhenNoCampaignIsFound() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> campaignService.sendEmailToCampaign(1L, "Test email content"));

    }

}
