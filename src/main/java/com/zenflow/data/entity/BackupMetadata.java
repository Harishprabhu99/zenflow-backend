package com.zenflow.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "backup_metadata")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackupMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "drive_file_id")
    private String driveFileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    private String checksum;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BackupStatus status = BackupStatus.COMPLETED;

    @Column(name = "backed_up_at")
    @CreationTimestamp
    private OffsetDateTime backedUpAt;
}
