package com.zenflow.data.repository;

import com.zenflow.data.entity.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OauthTokenRepository extends JpaRepository<OauthToken, UUID> {
    Optional<OauthToken> findByUserId(UUID userId);
}
