package com.muc.rfid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
    private Long id;
    private String epc;
    private String tid;
    private LocalDateTime timestamp;
    private Integer antennaId;
    private Double rssi;
    private Integer count;
    private Double frequencyPoint;
    private Double phase;
    private String readerId;
    private String companyName;
    private String userData;
    private String reserved;
    private LocalDateTime createdAt;
}
