package org.io.GreenGame.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long recipientId;
    private LocalDateTime timestamp;
    private InvitationStatus status;

    public Invitation(Long senderId, Long recipientId, InvitationStatus status) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public Invitation() {

    }
}