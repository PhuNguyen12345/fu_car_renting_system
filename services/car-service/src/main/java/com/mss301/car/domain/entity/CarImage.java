package com.mss301.car.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "car_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;
}
