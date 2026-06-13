# MABENTECH — Global Laptop & Accessories Store
> The Future of Laptops, Delivered Globally

A full-stack, production-ready e-commerce platform built with **Java 17 + Spring Boot 3.2**, featuring enterprise-grade security, a rich dark-tech UI, and global-ready architecture.

---

## 🚀 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.2 |
| Security | Spring Security 6, JWT (jjwt 0.11.5), BCrypt |
| Database | H2 (dev) → PostgreSQL (prod) |
| ORM | Spring Data JPA / Hibernate |
| Templating | Thymeleaf 3 + Thymeleaf Security |
| Frontend | HTML5, CSS3 (custom design system), Vanilla JS |
| Fonts | Inter + Space Grotesk (Google Fonts) |
| Icons | Font Awesome 6 |
| Build | Maven 3.9 |

---

## ⚡ Quick Start

### Prerequisites
- Java 17+
- Maven 3.9+

### Run locally
```bash
git clone https://github.com/yourname/mabentech.git
cd mabentech
mvn spring-boot:run
```
Open → **http://localhost:8080**

### Admin login
```
Email:    admin@mabentech.com
Password: Admin@2025!
```

---

## 🏗️ Project Structure

```
src/main/java/com/mabentech/
├── MabenTechApplication.java      # Entry point
├── config/
│   └── DataSeeder.java            # Seeds 16 products + admin on startup
├── controller/
│   ├── StoreController.java       # All page routes (/, /shop, /product, etc.)
│   ├── ProductApiController.java  # REST API (/api/products, /api/search)
│   └── AuthController.java        # Register endpoint
├── model/
│   ├── Product.java               # Product entity
│   ├── User.java                  # User entity with roles
│   ├── Order.java                 # Order entity
│   └── OrderItem.java             # Order line items
├── repository/
│   ├── ProductRepository.java
│   ├── UserRepository.java
│   └── OrderRepository.java
├── security/
│   ├── SecurityConfig.java        # Spring Security + CORS + CSP headers
│   ├── JwtUtil.java               # Token generation/validation
│   ├── JwtAuthenticationFilter.java
│   └── UserDetailsServiceImpl.java

src/main/resources/
├── application.properties
├── static/
│   ├── css/main.css               # Full design system (~700 lines)
│   └── js/main.js                 # Cart, wishlist, search, toasts
└── templates/
    ├── layout.html                # Base layout with nav + footer
    ├── index.html                 # Homepage
    ├── shop.html                  # Shop/catalog page
    ├── product-detail.html        # Single product page
    ├── login.html                 # Sign in
    ├── register.html              # Create account
    ├── cart.html                  # Cart page
    ├── checkout.html              # Multi-step checkout
    ├── about.html                 # About us
    ├── contact.html               # Contact form
    └── error.html                 # 404/403/500
```

---

## 🔐 Security Features

- **Spring Security 6** — form login, session management, CSRF protection
- **JWT Authentication** — stateless API auth with HS256 signing
- **BCrypt password hashing** (strength 12)
- **Content Security Policy** headers
- **Referrer-Policy**, **X-Frame-Options**, **HSTS** (production)
- **Role-based access control** — CUSTOMER / ADMIN / MODERATOR
- **Session fixation protection**, max sessions per user

---

## 🌍 Pages

| Route | Description |
|---|---|
| `/` | Homepage — hero, featured, bestsellers, newsletter |
| `/shop` | Full catalog with sidebar filters & sort |
| `/shop?category=laptop` | Laptops only |
| `/shop?category=accessory` | Accessories only |
| `/shop?q=apple` | Search results |
| `/product/{id}` | Product detail with specs |
| `/cart` | Cart page |
| `/checkout` | 3-step checkout (shipping → payment → confirm) |
| `/login` | Sign in |
| `/register` | Create account |
| `/about` | About MABENTECH |
| `/contact` | Contact form |
| `/api/products` | REST: all products (JSON) |
| `/api/search?q=` | REST: search products (JSON) |

---

## 🗄️ Production Deployment

### Switch to PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mabentech
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
```

### Enable HTTPS
```properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_KEYSTORE_PASSWORD}
server.ssl.key-store-type=PKCS12
```

### Build JAR
```bash
mvn clean package -DskipTests
java -jar target/mabentech-store-1.0.0.jar
```

### Docker
```dockerfile
FROM eclipse-temurin:17-jre
COPY target/mabentech-store-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## 📦 Pre-loaded Products (16 total)

**Laptops (8):** MacBook Pro M3 Max, MacBook Air M3, Dell XPS 15 OLED, ASUS ROG Zephyrus G16, Lenovo ThinkPad X1 Carbon Gen 12, HP Spectre x360 14, Microsoft Surface Laptop 6, Acer Predator Helios Neo 18

**Accessories (8):** Logitech MX Keys S, Logitech MX Master 3S, CalDigit TS4 Thunderbolt 4 Dock, Samsung T9 Portable SSD 4TB, Keychron Q1 Pro, LG 32UN880-B UltraFine Monitor, Moshi Slim Carry Case, Anker 200W GaN Charger

---

## 🎨 Design System

- **Dark tech palette** — deep navy backgrounds, purple accent, teal success
- **Space Grotesk** for headings, **Inter** for body
- Responsive grid, sticky nav, animated hero, floating cards
- Cart & wishlist side drawers with live updates
- Multi-step checkout with progress indicator
- Toast notifications, search autocomplete, product sorting

---

© 2025 MABENTECH Global Ltd. All rights reserved.
