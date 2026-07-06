# 🚗 Hệ Thống Cho Thuê Xe Trực Tuyến (Online Car Rental System)

## 📖 Giới thiệu (Overview)
Đây là kho lưu trữ trung tâm (Monorepo) chứa toàn bộ mã nguồn Backend của hệ thống **Cho Thuê Xe Trực Tuyến**. Dự án được thiết kế để giải quyết bài toán quản lý xe, khách hàng, và tự động hóa toàn bộ quy trình thuê xe từ khâu đặt cọc đến lúc trả xe và thanh toán.

Dự án được phát triển theo kiến trúc **Microservices** kết hợp cấu trúc **Maven Multi-module**, giúp tối ưu hóa việc quản lý code tập trung, chia sẻ thư viện chung nhưng vẫn đảm bảo tính độc lập khi triển khai (Selective Build & Deploy) cho từng bộ phận nghiệp vụ.

## 🏢 Kiến trúc thư mục
Dự án áp dụng cấu trúc phân tách rõ ràng giữa hạ tầng kỹ thuật và logic nghiệp vụ:

```text
fu_car_renting_system/
├── services/                    # 📦 CORE BUSINESS SERVICES (Nghiệp vụ cốt lõi)
│   ├── car-service/             # Quản lý danh mục xe, bảo trì, giá cả
│   ├── customer-service/        # Quản lý hồ sơ khách hàng, GPLX
│   ├── renting-service/         # Quản lý tạo hợp đồng (booking), thanh toán
│   └── notification-service/    # Gửi email/SMS thông báo tự động
│
├── infra/                       # ⚙️ INFRASTRUCTURE SERVICES (Hạ tầng hệ thống)
│   ├── eureka-server/           # Service Registry & Discovery
│   ├── config-server/           # Centralized Configuration (Quản lý cấu hình tập trung)
│   └── api-gateway/             # Cổng giao tiếp duy nhất (Routing & Load balancing)
│
├── .gitignore                   # Lọc file rác, file build, cấu hình IDE
└── pom.xml                      # Root Parent POM (Quản lý tất cả module)
```

## 🛠 Công nghệ sử dụng (Tech Stack)
* **Ngôn ngữ:** Java 21
* **Framework chính:** Spring Boot 3.x
* **Cloud & Microservices:** Spring Cloud Netflix (Eureka), Spring Cloud Config, Spring Cloud Gateway, OpenFeign
* **Bảo mật & Xác thực:** Keycloak (OAuth2 / OIDC)
* **Quản lý dự án:** Maven
* **Cơ sở dữ liệu:** PostgreSQL (Kiến trúc Database-per-service)
* **Quản lý Schema:** Flyway Migration

## 📦 Danh sách các Services và Cổng (Ports)

| Tên Service | Port | Vai trò / Chức năng |
| :--- | :---: | :--- |
| **`eureka-server`** | **8761** | Dịch vụ trung tâm giúp các service tìm thấy nhau. |
| **`api-gateway`** | **8080** | Điểm đón lõng mọi request từ Frontend, điều hướng tới service nội bộ. |
| **`keycloak`** | **8180** | IAM Server quản lý user, cấp phát Token JWT. |
| **`car-service`** | **8081** | Quản lý thông tin, trạng thái, và giá xe. |
| **`customer-service`** | **8082** | Quản lý thông tin khách hàng. |
| **`notification-service`**| **8083** | Xử lý gửi tin nhắn/email cho khách. |
| **`renting-service`** | **8084** | Chịu trách nhiệm logic nghiệp vụ thuê xe. |

## 🚀 Hướng dẫn cài đặt & Khởi chạy (Local)

### 1. Yêu cầu hệ thống
* JDK 21
* Maven 3.8+
* IDE: IntelliJ IDEA (Khuyến nghị)
* Cài đặt và khởi chạy các database PostgreSQL cục bộ (`car_db`, `customer_db`, `rental_db`, `notification_db`).

### 2. Thứ tự khởi động BẮT BUỘC
Để hệ thống khởi chạy ổn định, không bị lỗi kết nối chéo, vui lòng chạy theo đúng thứ tự:
1. **Keycloak (`8180`)**: Khởi động trước để hệ thống bảo mật sẵn sàng.
2. **Eureka Server (`8761`)**: Khởi động để sẵn sàng nhận đăng ký từ các service.
3. **API Gateway (`8080`)**: Khởi động sau Keycloak để tải thông tin xác thực.
4. **Các Business Services (8081-8084)**: Bật song song `car-service`, `customer-service`, `renting-service`, `notification-service`.

## 👨‍💻 Tác giả
- **Tác giả:** Nguyễn An Phú
- **MSSV:** HE191790
- **Trường:** Đại học FPT
