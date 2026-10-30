# Abaca E-Commerce

A multi-vendor online marketplace. Buyers browse and search products, keep a cart, and check out. Sellers register a shop, list products, and ship orders.

## Stack

- Java 23, Spring Boot 4.0.6 (Maven wrapper included)
- Spring Data JPA + PostgreSQL
- Spring Security (form login, BCrypt, role-based access)
- Thymeleaf with the layout dialect (server-rendered pages)
- MapStruct + Lombok, Bean Validation

## What it does

**Buyers**
- Browse the home page, categories (`/category/{id}`), shops (`/shop/{id}`), product pages and search (`/search`)
- Cart (`/cart`) and checkout (`/checkout`)
- Account area (`/account`, `/buyer`): profile, saved addresses, purchase history, order detail

**Sellers** (role `SELLER`, under `/seller`)
- Onboarding via `/start-selling`
- Dashboard, shop profile, product add / edit / delete (with images), incoming orders and marking them shipped

**REST API** (`/api/v1/...`): CRUD-style endpoints for users, buyers, sellers, products, categories, carts, cart items, orders, order items and payments.

**Security**: public pages are `/`, `/login`, `/register`, `/products/**`, `/category/**`, `/search`, `/shop/**` and static assets. `/seller/**` needs the `SELLER` role. Everything else needs login.

## Project layout

```
src/main/java/com/online/abaca/
  controller/   page controllers + REST controllers
  service/      service interfaces and *Impl classes
  repository/   Spring Data JPA repositories
  model/        JPA entities (UserAccount, Buyer, Seller, Product, Category,
                Address, CartHead/CartItem, OrderHead/OrderItem, Payment)
  dto/          request/response DTOs (mapped with MapStruct)
  config/       SecurityConfig, CorsConfig
src/main/resources/
  templates/    Thymeleaf pages, layout.html, fragments/
  static/       css/style.css, js/main.js
graphify-out/   knowledge graph of this codebase (see below)
```

## Run it

1. Create a PostgreSQL database `abaca_ecommerce_db` (default config: `localhost:5432`, user `postgres`).
2. Set the datasource password in `src/main/resources/application.properties`.
3. Start the app:

```bash
./mvnw spring-boot:run
```

The app listens on <http://localhost:5050>. Tables are created automatically (`ddl-auto=update`).

## Code knowledge graph

`graphify-out/` holds a [graphify](https://github.com/safishamsi/graphify) map of the code and templates:

- `GRAPH_REPORT.md`: key classes, communities and suggested questions
- `graph.html`: interactive graph, open in a browser
- `graph.json`: raw graph data for querying

Rebuild it with `/graphify . --update` in Claude Code.

## Known gaps

- Only the default `contextLoads` test exists.
- No global exception handling layer.
