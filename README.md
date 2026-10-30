# Abaca E-Commerce

A localized e-commerce platform that connects the abaca artisans of Albay with buyers.

## About

Abaca E-Commerce is a web application written with Java and Spring Boot. Anyone can browse and search products. Registered buyers can fill a cart, save addresses and check out. Registered sellers get their own shop, a dashboard, and tools to manage products and orders.

## Why It Was Built

- **Purpose:** to connect local abaca artisans in Albay with potential buyers.
- **Technical goal:** to demonstrate database design, structural integrity through normalization, and practical data manipulation with SQL operations.

## What It Does

- **Product search and filtering:** buyers query products by category, price range and search terms.
- **Order processing and inventory:** checkout runs as a transaction (inserts and updates) that creates the order and automatically deducts the purchased quantity from product stock.
- **Sales analytics:** aggregate functions (order counts, units sold, price statistics) grouped by product ID produce performance reports for platform administrators.
- **Database maintenance:** logical delete queries clean up corrupted or abandoned shopping carts to keep the database lean and structured.

## Features

**For buyers**
- Browse by category, view shop pages, search products
- Product detail pages with images
- Shopping cart and checkout
- Profile, saved addresses, purchase history and order details

**For sellers**
- Apply to become a seller ("Start selling")
- Seller dashboard and shop profile
- Add, edit and delete products with images
- View incoming orders and mark them as shipped

**General**
- Registration and login with encrypted passwords (BCrypt)
- Role-based access control (buyer and seller)
- REST API under `/api/v1` for users, buyers, sellers, products, categories, carts, orders and payments

## Built With

- Java 23
- Spring Boot 4 (Web MVC, Data JPA, Security, Validation)
- Thymeleaf
- PostgreSQL
- MapStruct and Lombok
- Maven

## Getting Started

### Prerequisites

- JDK 23
- PostgreSQL

### Installation

1. Clone the repository

   ```bash
   git clone https://github.com/Caelira/Abaca-E-Commerce.git
   cd Abaca-E-Commerce
   ```

2. Create a PostgreSQL database named `abaca_ecommerce_db`.

3. Set your database password in `src/main/resources/application.properties`.

4. Run the app

   ```bash
   ./mvnw spring-boot:run
   ```

5. Open <http://localhost:5050>

## Roadmap

- Add automated tests (only a basic context test exists)
- Add a global error-handling layer
- Real payment and shipping integration

## Author

**Prince II L. Olaguera**
GitHub: [@Caelira](https://github.com/Caelira)
