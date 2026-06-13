package com.zenflow.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "google_id", unique = true)
    private String googleId;

    private String email;
    private String password;
    private String displayName;
    private String avatarUrl;

    @Column(name = "is_guest", nullable = false)
    @Builder.Default
    private boolean isGuest = true;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "last_seen_at")
    @UpdateTimestamp
    private OffsetDateTime lastSeenAt;
}
