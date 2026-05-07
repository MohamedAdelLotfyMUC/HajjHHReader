package com.muc.rfid.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagUploadRequest {
    @NotBlank(message = "EPC is required")
    private String epc;
    
    private String tid;
    
    private LocalDateTime timestamp;
    
    private Integer antennaId;
    
    private Double rssi;
    
    private Integer count;
    
    private Double frequencyPoint;
    
    private Double phase;
    
    @NotBlank(message = "Reader ID is required")
    private String readerId;

    @NotBlank(message = "Company Name is required")
    private String companyName;
    
    private String userData;
    
    private String reserved;
}
