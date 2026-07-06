# 👤 Customer Service

## 📖 Giới thiệu (Overview)
**Customer Service** là microservice chịu trách nhiệm lưu trữ và quản lý thông tin hồ sơ của khách hàng trong hệ thống Thuê Xe Trực Tuyến.

## 🎯 Phân tích nghiệp vụ (Business Logic)
Các chức năng chính do service đảm nhận:
1. **Hồ sơ cá nhân:** Lưu trữ thông tin cơ bản (Họ tên, SĐT, Địa chỉ, Email).
2. **Xác thực giấy tờ:** Quản lý Giấy phép lái xe (GPLX), CCCD/CMND để xác minh điều kiện thuê xe.
3. **Hạng thẻ & Điểm thưởng:** Quản lý hạng thành viên (Vàng, Bạc, Đồng) để tính chiết khấu khi thuê xe.
4. **Đồng bộ User:** Đồng bộ dữ liệu định danh (Identity) với Keycloak sau khi khách hàng đăng ký tài khoản.

## 📁 Cấu trúc thư mục (Folder Structure)
Service tuân thủ nghiêm ngặt **Layered Architecture**:

```text
src/main/java/com/mss301/customer/
├── api/ (hoặc controller/)       # 🌐 Cung cấp các Endpoint RESTful (GET /api/v1/customers/...).
├── application/ (hoặc service/)  # ⚙️ Chứa logic nghiệp vụ (ví dụ: Rule kiểm tra tuổi, hạn GPLX).
├── domain/ (hoặc entity/model)   # 📦 Chứa Entity (Customer, License, Address).
├── infrastructure/ (hoặc repo/)  # 🗄️ Giao tiếp với `customer_db` (PostgreSQL).
├── dto/                          # ✉️ Chứa các Class Request/Response để Validate dữ liệu đầu vào.
└── exception/                    # 🚨 Bắt lỗi như CustomerNotFoundException, InvalidLicenseException.
```

## 🛠 Database Schema (Dự kiến)
- `customers`: id, keycloak_id, full_name, phone, email, status, membership_tier.
- `driving_licenses`: id, customer_id, license_number, issue_date, expiry_date, status.
- *(Quản lý qua Flyway Migration)*
