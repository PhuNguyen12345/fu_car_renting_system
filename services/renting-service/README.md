# 📜 Renting Service

## 📖 Giới thiệu (Overview)
**Renting Service** là "trái tim" của toàn bộ hệ thống. Đây là nơi diễn ra quy trình thuê xe phức tạp nhất, giao tiếp với hầu hết các service khác (Car, Customer, Notification) để hoàn thành một giao dịch.

## 🎯 Phân tích nghiệp vụ (Business Logic)
Các chức năng chính:
1. **Tạo hợp đồng thuê (Booking):** Nhận yêu cầu thuê, gọi sang `car-service` để kiểm tra trạng thái xe, gọi sang `customer-service` để check GPLX.
2. **Tính toán chi phí:** Tính tổng tiền dựa trên số ngày, phí bảo hiểm, phí trễ hạn (nếu có).
3. **Quản lý trạng thái:** Chuyển đổi trạng thái đơn thuê: `PENDING` -> `APPROVED` -> `IN_PROGRESS` -> `COMPLETED` / `CANCELED`.
4. **Trả xe & Đánh giá:** Ghi nhận xe hoàn trả, kiểm tra hỏng hóc và kết thúc hợp đồng.

## 📁 Cấu trúc thư mục (Folder Structure)
Vì nghiệp vụ phức tạp, cấu trúc thư mục được phân chia chi tiết:

```text
src/main/java/com/mss301/renting/
├── api/ (hoặc controller/)       # 🌐 Expose các API liên quan đến đơn thuê.
├── application/ (hoặc service/)  # ⚙️ Chứa Use-Cases logic chính (Tạo đơn, Hủy đơn).
├── domain/ (hoặc entity/model)   # 📦 Chứa Entity (RentalContract, PaymentDetails).
├── infrastructure/ 
│   ├── client/                   # 🔌 Chứa OpenFeign Clients để gọi sang car-service, customer-service.
│   └── repository/               # 🗄️ JPA Repositories (PostgreSQL - rental_db).
├── dto/                          # ✉️ Request/Response Payload.
└── exception/                    # 🚨 Xử lý ngoại lệ phân tán (CarNotAvailableException).
```

## 🛠 Database Schema (Dự kiến)
- `rentals`: id, customer_id, car_id, start_date, end_date, total_price, status.
- `rental_payments`: id, rental_id, amount, payment_method, payment_status.
