package com.muc.rfid.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tag_reads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagRead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String epc;

    private String tid;

    private LocalDateTime timestamp;

    private Integer antennaId;

    private Double rssi;

    private Integer count;

    private Double frequencyPoint;

    private Double phase;

    @Column(nullable = false)
    private String readerId;

    private String userData;

    private String reserved;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String rawPayload;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
