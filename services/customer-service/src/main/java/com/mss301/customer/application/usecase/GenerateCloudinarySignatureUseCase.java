package com.mss301.customer.application.usecase;

import com.mss301.customer.api.dto.CloudinarySignatureDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class GenerateCloudinarySignatureUseCase {

    @Value("${cloudinary.api-key:YOUR_API_KEY}")
    private String apiKey;

    @Value("${cloudinary.api-secret:YOUR_API_SECRET}")
    private String apiSecret;
    
    @Value("${cloudinary.cloud-name:YOUR_CLOUD_NAME}")
    private String cloudName;

    public CloudinarySignatureDto execute() {
        long timestamp = System.currentTimeMillis() / 1000L;
        String folder = "mss301/fpt_car_rental_assets/customers";
        String strToSign = "folder=" + folder + "&timestamp=" + timestamp + apiSecret;
        
        String signature = hashSHA1(strToSign);

        return CloudinarySignatureDto.builder()
                .signature(signature)
                .timestamp(timestamp)
                .apiKey(apiKey)
                .cloudName(cloudName)
                .folder(folder)
                .build();
    }

    private String hashSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi sinh chữ ký Cloudinary", e);
        }
    }
}
