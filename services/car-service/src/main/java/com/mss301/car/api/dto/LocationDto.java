package com.mss301.car.api.dto;
import com.mss301.car.domain.entity.Location;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class LocationDto {
    private UUID id;
    private String name;
    private String city;
    private String address;

    public static LocationDto fromEntity(Location location) {
        return LocationDto.builder()
                .id(location.getId())
                .name(location.getName())
                .city(location.getCity())
                .address(location.getAddress())
                .build();
    }
}
