package com.mss301.car.domain.aggregate;

import com.mss301.car.domain.entity.Car;
import com.mss301.car.domain.entity.CarImage;
import com.mss301.car.domain.vo.CarStatus;
import java.util.List;
import java.util.ArrayList;

public class CarAggregate {
    private final Car car;

    // Private constructor (Đúng chuẩn không dùng public constructor cho Aggregate Root)
    private CarAggregate(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car entity cannot be null");
        }
        this.car = car;
    }

    // Factory method để khởi tạo Aggregate
    public static CarAggregate fromEntity(Car car) {
        return new CarAggregate(car);
    }

    // Lấy Entity ra để lưu vào DB khi cần
    public Car getEntity() {
        return this.car;
    }

    // Domain Logic: Các hàm xử lý nghiệp vụ nội tại
    public void lockForBooking() {
        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Xe không ở trạng thái AVAILABLE để khóa");
        }
        car.setStatus(CarStatus.LOCKED_PENDING);
    }

    public void unlock() {
        car.setStatus(CarStatus.AVAILABLE);
    }

    public void confirmRented() {
        car.setStatus(CarStatus.RENTED);
    }

    public void setFeatures(List<String> features) {
        car.setFeatures(features != null ? features : new ArrayList<>());
    }

    public void updateImages(String primaryUrl, List<String> galleryUrls) {
        car.getImages().clear();
        
        if (primaryUrl != null) {
            car.getImages().add(CarImage.builder().car(car).imageUrl(primaryUrl).isPrimary(true).build());
        }
        
        if (galleryUrls != null) {
            galleryUrls.forEach(url -> 
                car.getImages().add(CarImage.builder().car(car).imageUrl(url).isPrimary(false).build())
            );
        }
    }

    public void updateDetails(String name, com.mss301.car.domain.entity.Brand brand, com.mss301.car.domain.entity.Location location, 
                              String type, java.math.BigDecimal price, Integer seats, String transmission, 
                              String fuelType, String fuelConsumption, String licensePlate, 
                              String description, CarStatus status) {
        car.setName(name);
        car.setBrand(brand);
        car.setLocation(location);
        car.setType(type);
        car.setPricePerDay(price);
        car.setSeats(seats);
        car.setTransmission(transmission);
        car.setFuelType(fuelType);
        car.setFuelConsumption(fuelConsumption);
        car.setLicensePlate(licensePlate);
        car.setDescription(description);
        if(status != null) {
            car.setStatus(status);
        }
    }

    public void changeStatus(CarStatus status) {
        if(status == null) throw new IllegalArgumentException("Status không được null");
        car.setStatus(status);
    }

    public void softDelete() {
        car.setDeletedAt(java.time.LocalDateTime.now());
        car.setStatus(CarStatus.UNAVAILABLE);
    }

    public void restore() {
        car.setDeletedAt(null);
        car.setStatus(CarStatus.AVAILABLE);
    }
}
