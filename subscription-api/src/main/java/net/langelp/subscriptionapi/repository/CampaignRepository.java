package net.langelp.subscriptionapi.repository;

import net.langelp.subscriptionapi.repository.entity.CampaignEntity;
import org.springframework.data.repository.CrudRepository;

public interface CampaignRepository extends CrudRepository<CampaignEntity, Long> {
}
