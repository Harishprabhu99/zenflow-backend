package com.zenflow.data.repository;

import com.zenflow.data.entity.BackupMetadata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BackupMetadataRepository extends JpaRepository<BackupMetadata, UUID> {
    Optional<BackupMetadata> findTopByUserIdOrderByBackedUpAtDesc(UUID userId);
    List<BackupMetadata> findByUserIdOrderByBackedUpAtDesc(UUID userId, Pageable pageable);
}
