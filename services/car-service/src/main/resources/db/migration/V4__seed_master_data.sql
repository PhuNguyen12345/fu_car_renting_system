-- Seed Brands
INSERT INTO brands (id, name, logo_url) VALUES 
('b1111111-1111-1111-1111-111111111111', 'Toyota', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg'),
('b2222222-2222-2222-2222-222222222222', 'Honda', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg'),
('b3333333-3333-3333-3333-333333333333', 'VinFast', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg'),
('b4444444-4444-4444-4444-444444444444', 'Ford', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg'),
('b5555555-5555-5555-5555-555555555555', 'Kia', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg');

-- Seed Locations
INSERT INTO locations (id, name, city, address) VALUES 
('a1111111-1111-1111-1111-111111111111', 'Trụ sở chính Hà Nội', 'Hà Nội', 'Tòa nhà FPT, Phạm Văn Bạch, Cầu Giấy'),
('a2222222-2222-2222-2222-222222222222', 'Chi nhánh TP.HCM', 'TP.HCM', 'Quận 9, TP. Thủ Đức'),
('a3333333-3333-3333-3333-333333333333', 'Chi nhánh Đà Nẵng', 'Đà Nẵng', 'Khu CNC Hòa Lạc, Đà Nẵng');

-- Seed Cars
INSERT INTO cars (id, name, brand_id, type, price_per_day, seats, transmission, fuel_type, fuel_consumption, license_plate, status, location_id, description) VALUES 
('c1111111-1111-1111-1111-111111111111', 'Toyota Vios 2023', 'b1111111-1111-1111-1111-111111111111', 'SEDAN', 800000, 5, 'AUTO', 'GASOLINE', '6L/100km', '30A-12345', 'AVAILABLE', 'a1111111-1111-1111-1111-111111111111', 'Xe mới tinh, sạch sẽ'),
('c2222222-2222-2222-2222-222222222222', 'Honda Civic', 'b2222222-2222-2222-2222-222222222222', 'SEDAN', 1000000, 5, 'AUTO', 'GASOLINE', '7L/100km', '51G-56789', 'AVAILABLE', 'a2222222-2222-2222-2222-222222222222', 'Xe thể thao, cá tính'),
('c3333333-3333-3333-3333-333333333333', 'VinFast VF8', 'b3333333-3333-3333-3333-333333333333', 'SUV', 1500000, 5, 'AUTO', 'ELECTRIC', 'Điện', '30F-99999', 'AVAILABLE', 'a1111111-1111-1111-1111-111111111111', 'Xe điện thông minh'),
('c4444444-4444-4444-4444-444444444444', 'Ford Everest', 'b4444444-4444-4444-4444-444444444444', 'SUV', 1300000, 7, 'AUTO', 'DIESEL', '8L/100km', '43A-11111', 'AVAILABLE', 'a3333333-3333-3333-3333-333333333333', 'Xe gầm cao mạnh mẽ'),
('c5555555-5555-5555-5555-555555555555', 'Kia Carnival', 'b5555555-5555-5555-5555-555555555555', 'MPV', 1800000, 7, 'AUTO', 'DIESEL', '9L/100km', '51H-22222', 'AVAILABLE', 'a2222222-2222-2222-2222-222222222222', 'Xe gia đình siêu rộng');

-- Seed Car Images
INSERT INTO car_images (id, car_id, image_url, is_primary) VALUES 
('d1111111-1111-1111-1111-111111111111', 'c1111111-1111-1111-1111-111111111111', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg', TRUE),
('d2222222-2222-2222-2222-222222222222', 'c2222222-2222-2222-2222-222222222222', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg', TRUE),
('d3333333-3333-3333-3333-333333333333', 'c3333333-3333-3333-3333-333333333333', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg', TRUE),
('d4444444-4444-4444-4444-444444444444', 'c4444444-4444-4444-4444-444444444444', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg', TRUE),
('d5555555-5555-5555-5555-555555555555', 'c5555555-5555-5555-5555-555555555555', 'https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg', TRUE);
