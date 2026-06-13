package com.zenflow.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "oauth_tokens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "refresh_token", nullable = false, columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "access_token", nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "expiry_date", nullable = false)
    private OffsetDateTime expiryDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
