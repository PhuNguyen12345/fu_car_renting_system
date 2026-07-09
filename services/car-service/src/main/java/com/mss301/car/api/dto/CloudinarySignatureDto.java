package com.mss301.car.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CloudinarySignatureDto {
    private String signature;
    private long timestamp;
    private String apiKey;
    private String cloudName;
    private String folder;
}
