# Danh sách món ăn - CRUD Android App

## Mô tả
Ứng dụng Android quản lý danh sách món ăn với đầy đủ chức năng CRUD (Create, Read, Update, Delete) sử dụng Intent để giao tiếp giữa các Activity.

## Tính năng chính

### ✅ Hiển thị danh sách
- Sử dụng RecyclerView với adapter tùy chỉnh
- Hiển thị ảnh, tên món, mô tả và giá
- Empty state khi chưa có dữ liệu
- Material Design với CardView

### ✅ Thêm món mới
- FloatingActionButton để thêm món
- Mở EditActivity bằng Intent
- Form validation (tên món bắt buộc)
- Trả kết quả về MainActivity

### ✅ Sửa món
- Tap vào item để sửa
- Truyền dữ liệu hiện tại qua Intent
- Cập nhật đúng vị trí trong danh sách

### ✅ Xóa món
- Long press để xóa
- Dialog xác nhận trước khi xóa
- Cập nhật UI ngay lập tức

## Kiến trúc

### Pattern: MVVM
- **Model**: `Item` data class với Parcelable
- **View**: MainActivity, EditActivity với ViewBinding
- **ViewModel**: MainViewModel với LiveData

### Package Structure
```
com.example.thangnt/
├── data/
│   ├── model/Item.kt
│   └── repository/InMemoryRepository.kt
├── ui/
│   ├── main/
│   │   ├── MainActivity.kt
│   │   ├── MainViewModel.kt
│   │   └── ItemAdapter.kt
│   └── edit/
│       └── EditActivity.kt
└── util/
    └── IntentKeys.kt
```

## Intent Contract

### Keys
- `EXTRA_MODE`: "mode_add" hoặc "mode_edit"
- `EXTRA_ITEM`: Item object (Parcelable)
- `EXTRA_POSITION`: Vị trí item trong list
- `EXTRA_ITEM_UPDATED`: Item đã được cập nhật

### Result Codes
- `RESULT_OK_ADD` (1001): Thêm thành công
- `RESULT_OK_EDIT` (1002): Sửa thành công
- `RESULT_CANCELED` (1003): Hủy thao tác

## Công nghệ sử dụng

- **Language**: Kotlin
- **UI**: Material Design Components
- **Architecture**: MVVM với LiveData
- **Navigation**: Activity Result API
- **Data**: In-memory storage (có thể mở rộng với Room)

## Yêu cầu hệ thống

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)
- **Build Tools**: Gradle 8.12.3

## Cài đặt và chạy

1. Clone repository
2. Mở project trong Android Studio
3. Sync Gradle
4. Chạy trên emulator hoặc thiết bị thật

## Screenshots

### Màn hình chính
- Danh sách món ăn với RecyclerView
- FloatingActionButton để thêm món
- Empty state khi chưa có dữ liệu

### Màn hình thêm/sửa
- Form với TextInputLayout
- Validation real-time
- Nút Lưu/Hủy

## Demo Video
Video demo ngắn (≤ 2 phút) bao gồm:
1. Thêm món mới
2. Sửa món hiện có
3. Xóa món với xác nhận
4. Empty state

## Mở rộng (tùy chọn)

### Room Database
- Thay thế InMemoryRepository bằng Room
- Lưu trữ vĩnh viễn

### Tính năng bổ sung
- Search/Filter
- Sort theo tên/giá
- Share item
- Unit tests với Espresso

## Phiên bản
- **Version**: 1.0
- **Build**: Debug APK available

## Tác giả
ThangNT - PRM392 Lab 4 Intent
