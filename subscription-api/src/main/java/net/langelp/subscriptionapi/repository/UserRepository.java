package net.langelp.subscriptionapi.repository;

import net.langelp.subscriptionapi.repository.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, String> {
}
