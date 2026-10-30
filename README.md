# Abaca E-Commerce

A multi-vendor online marketplace where buyers shop from many independent sellers in one place.

## About

Abaca E-Commerce is a full-stack web application built with Java and Spring Boot. Anyone can browse and search products. Registered buyers can fill a cart, save delivery addresses and check out. Registered sellers get their own shop, a dashboard, and tools to manage products and orders.

## Why I Built This

I built Abaca to learn how a real online marketplace works end to end: user accounts and roles, a relational data model, a cart-to-order flow, and a server-rendered front end. It is also a base for a platform that lets small sellers set up a shop without building their own website.

## Features

**For buyers**
- Browse products by category, view shop pages, and search
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
