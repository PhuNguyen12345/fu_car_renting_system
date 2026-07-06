# 🔔 Notification Service

## 📖 Giới thiệu (Overview)
**Notification Service** là một service phụ trợ (Utility Microservice) chuyên biệt hóa cho việc gửi tin nhắn, cảnh báo và thông báo đến người dùng.

## 🎯 Phân tích nghiệp vụ (Business Logic)
Các chức năng chính:
1. **Gửi Email Xác Nhận:** Gửi hóa đơn và thông tin chi tiết khi khách hàng đặt xe thành công (từ `renting-service`).
2. **Nhắc nhở trả xe:** Gửi SMS hoặc Email tự động khi sắp đến hạn trả xe.
3. **Thông báo hệ thống:** Thông báo bảo trì, cảnh báo tài khoản.

*Lưu ý: Service này thường hoạt động dưới dạng Event-Driven (nhận Message từ Kafka/RabbitMQ) hoặc qua các API gọi nội bộ.*

## 📁 Cấu trúc thư mục (Folder Structure)

```text
src/main/java/com/mss301/notification/
├── api/ (hoặc controller/)       # 🌐 API để các service khác gọi đến (hoặc Consumer nhận Kafka Message).
├── application/ (hoặc service/)  # ⚙️ Logic định tuyến tin nhắn (Quyết định gửi SMS hay Email).
├── domain/ (hoặc entity/model)   # 📦 Chứa Entity lưu trữ lịch sử thông báo.
├── infrastructure/ 
│   ├── email/                    # 📧 Tích hợp Spring Mail / SendGrid.
│   ├── sms/                      # 📱 Tích hợp Twilio / SMS Gateway.
│   └── repository/               # 🗄️ JPA để lưu Log.
├── dto/                          # ✉️ Thông điệp cấu trúc hóa.
└── config/                       # 🛠 Cấu hình SMTP, Kafka...
```

## 🛠 Database Schema (Dự kiến)
- `notification_logs`: id, recipient, type (EMAIL/SMS), status (SENT/FAILED), content, created_at.
