# 🚗 Car Service

## 📖 Giới thiệu (Overview)
**Car Service** là một microservice cốt lõi trong hệ thống Thuê Xe Trực Tuyến. Service này chịu trách nhiệm quản lý toàn bộ vòng đời và thông tin của các phương tiện (xe ô tô) có trong hệ thống.

## 🎯 Phân tích nghiệp vụ (Business Logic)
Các chức năng chính do service đảm nhận:
1. **Quản lý danh mục xe:** Thêm, sửa, xóa thông tin xe (Hãng xe, Dòng xe, Biển số, Năm sản xuất, Số ghế).
2. **Quản lý trạng thái xe:** Theo dõi xe đang rảnh (`AVAILABLE`), đang cho thuê (`RENTED`), hoặc đang bảo trì (`MAINTENANCE`).
3. **Quản lý giá thuê:** Cấu hình giá thuê theo ngày, theo giờ và các phụ phí.
4. **Tìm kiếm & Lọc:** Cung cấp API tìm kiếm xe cho khách hàng (theo loại xe, giá, hãng, v.v.).

## 📁 Cấu trúc thư mục (Folder Structure)
Service được thiết kế theo mô hình **Layered Architecture** (Kiến trúc phân tầng) để đảm bảo Separation of Concerns:

```text
src/main/java/com/mss301/car/
├── api/ (hoặc controller/)       # 🌐 Chứa các REST Controller tiếp nhận HTTP Request từ API Gateway.
├── application/ (hoặc service/)  # ⚙️ Chứa business logic, xử lý các rule nghiệp vụ của xe.
├── domain/ (hoặc entity/model)   # 📦 Chứa các thực thể JPA (Car, Brand, Category) ánh xạ xuống DB.
├── infrastructure/ (hoặc repo/)  # 🗄️ Chứa Spring Data JPA Repositories để giao tiếp với PostgreSQL.
├── dto/                          # ✉️ Data Transfer Objects để nhận/trả dữ liệu cho Client mà không lộ Entity.
└── exception/                    # 🚨 Xử lý lỗi tập trung (GlobalExceptionHandler) và các Custom Exceptions.
```

## 🛠 Database Schema (Dự kiến)
- `cars`: id, license_plate, brand_id, model, year, status, daily_rate.
- `brands`: id, name.
- *(Sẽ được quản lý tự động thông qua Flyway Migration trong thư mục `src/main/resources/db/migration`)*
