package net.langelp.subscriptionapi.fixtures;

import net.langelp.subscriptionapi.repository.entity.CampaignEntity;

import java.util.Set;

public class CampaignEntityFixtures {
    public static CampaignEntity someValidEntity() {
        var entity = new CampaignEntity();
        entity.setTitle("Test campaign");
        entity.setId(1L);
        entity.setUsers(Set.of(UserEntityFixtures.someValidEntity("a@a.es"), UserEntityFixtures.someValidEntity("b@b.es")));
        return entity;
    }
}
