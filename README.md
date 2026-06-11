# 📦 ShipZen - Parcel Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

**ShipZen** is a comprehensive, full-stack web application designed to streamline parcel logistics. It provides dedicated, role-based portals for both **Customers** and **Logistics Officers**, enabling seamless booking, real-time tracking, and efficient delivery management.

---

## ✨ Features

### 👤 Customer Portal
- **Easy Booking**: Intuitive form to schedule parcel pickups with auto-calculated estimated costs.
- **Real-Time Tracking**: Visual timeline tracking of parcel journey (Booked → Picked Up → In Transit → Delivered).
- **Booking History**: View past shipments with status badges and invoice generation.
- **Customer Support**: Dedicated contact and support information page.

### 👮 Officer Portal
- **Live Dashboard**: Real-time statistics showing Total, Pending, In Transit, and Delivered parcels.
- **Global Tracking & Search**: Filter and search all system bookings by ID, customer, or receiver name.
- **Delivery Updates**: Update parcel status, current location, delivery agent, and add remarks.
- **Pickup Scheduling**: Assign officers and schedule specific pickup dates/times for pending bookings.

### ⚙️ System Features
- **Role-Based Access Control**: Secure session-based authentication for `CUSTOMER` and `OFFICER` roles.
- **Auto-Seeded Data**: Pre-configured default accounts for immediate testing out-of-the-box.
- **Responsive UI**: Modern, mobile-friendly interface built with Bootstrap 5 and custom CSS (ShipZen warm theme).
- **In-Memory Database**: H2 database with an enabled web console for easy debugging and data inspection.

---

## 🛠️ Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 3.2.5, Spring Web, Spring Data JPA |
| **Frontend** | Thymeleaf, HTML5, CSS3, JavaScript, Bootstrap 5, Bootstrap Icons |
| **Database** | H2 In-Memory Database |
| **Build Tool** | Apache Maven (with Maven Wrapper) |

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)** 17 or higher
- **Maven** 3.6+ (or use the included Maven Wrapper)

### Installation & Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/vikashverse-parcel-management.git
   cd vikashverse-parcel-management
   ```

2. **Build and Run the application:**
   - **On macOS/Linux:**
     ```bash
     ./mvnw spring-boot:run
     ```
   - **On Windows:**
     ```cmd
     mvnw.cmd spring-boot:run
     ```

3. **Access the application:**
   Open your browser and navigate to: [http://localhost:8080](http://localhost:8080)

---

## 🔑 Default Credentials

The application automatically seeds the following accounts on startup (via `DataSeeder.java`):

| Role | Email | Password |
| :--- | :--- | :--- |
| **Officer** | `officer@shipzen.com` | `Officer@123` |
| **Customer** | `customer@shipzen.com` | `Customer@123` |

*(You can also register new customer accounts via the `/login#register` page).*

---

## 🗄️ Database Access (H2 Console)

Since the project uses an in-memory H2 database, you can inspect the data visually:
1. Navigate to: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. Use the following connection settings:
   - **Saved Settings**: `Generic H2 (Embedded)`
   - **JDBC URL**: `jdbc:h2:mem:parceldb`
   - **User Name**: `sa`
   - **Password**: *(leave blank)*
3. Click **Connect**.

---

## 📂 Project Structure

```text
vikashverse-parcel-management/
├── .mvn/wrapper/          # Maven wrapper properties
├── src/
│   ├── main/
│   │   ├── java/com/tcs/ilp/ParcelManagement/
│   │   │   ├── config/        # DataSeeder (Default accounts)
│   │   │   ├── controller/    # REST/Web Controllers (Auth, Customer, Officer, Home)
│   │   │   ├── model/         # JPA Entities (Booking, User)
│   │   │   ├── repository/    # Spring Data JPA Repositories
│   │   │   └── service/       # Business Logic (BookingService, UserService)
│   │   └── resources/
│   │       ├── application.properties # App configuration (H2, Thymeleaf, Port 8080)
│   │       ├── static/              # CSS and JS assets
│   │       └── templates/           # Thymeleaf HTML views
│   └── test/                # Unit/Integration tests
├── mvnw / mvnw.cmd          # Maven wrapper scripts
└── pom.xml                  # Maven dependencies and build config
```

---

## 📸 Application Screenshots

*(Optional: Add screenshots of your Landing Page, Customer Dashboard, and Officer Dashboard here to make the README even more attractive!)*

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project:
1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

Developed with ❤️ by **Vikash** (TCS ILP Project)  
*For inquiries, please reach out via the application's Support page or open an issue in this repository.*
```
