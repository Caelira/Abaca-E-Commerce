# Graph Report - Abaca-E-Commerce  (2026-10-30)

## Corpus Check
- Corpus is ~23,696 words - fits in a single context window. You may not need a graph.

## Summary
- 938 nodes · 2947 edges · 28 communities (16 shown, 12 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 370 edges (avg confidence: 0.84)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Shared Utilities & Exceptions
- DTO Validation
- Address Management
- Order Head API
- Template Pages & Routes
- Product Service
- Cart Item API
- Shared Template Fragments
- Order Item API
- REST Controller Annotations
- Category Module
- Payment API
- Buyer Module
- Cart Head API
- Security & App Config
- Account & Registration
- Seller Dashboard
- User Details Auth
- Custom User Details
- Repositories & Queries
- Global Controller Advice
- Frontend JS Modals
- Context Test
- App Entrypoint
- Maven Project

## God Nodes (most connected - your core abstractions)
1. `UserAccount` - 55 edges
2. `CustomUserDetails` - 46 edges
3. `Address` - 35 edges
4. `OrderHead` - 35 edges
5. `Buyer` - 34 edges
6. `ProductResponseDTO` - 33 edges
7. `Seller` - 30 edges
8. `CartHead` - 29 edges
9. `OrderItem` - 28 edges
10. `ProductService` - 28 edges

## Surprising Connections (you probably didn't know these)
- `seller-product-add.html (add product form)` --semantically_similar_to--> `seller-products.html (seller inventory list)`  [INFERRED] [semantically similar]
  src/main/resources/templates/seller-product-add.html → src/main/resources/templates/seller-products.html
- `shop-view.html (public shop page)` --shares_data_with--> `Model attrs: keyword, shops, products`  [INFERRED]
  src/main/resources/templates/shop-view.html → src/main/resources/templates/search-results.html
- `SecurityConfig` --references--> `CustomUserDetailsService`  [EXTRACTED]
  src/main/java/com/online/abaca/config/SecurityConfig.java → src/main/java/com/online/abaca/userdetails/CustomUserDetailsService.java
- `AccountController` --references--> `AddressService`  [EXTRACTED]
  src/main/java/com/online/abaca/controller/AccountController.java → src/main/java/com/online/abaca/service/AddressService.java
- `BuyerDashboardController` --references--> `OrderItemRepository`  [EXTRACTED]
  src/main/java/com/online/abaca/controller/BuyerDashboardController.java → src/main/java/com/online/abaca/repository/OrderItemRepository.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Account area pages sharing sidebar nav** — src_main_resources_templates_account_addresses, src_main_resources_templates_addresses, src_main_resources_templates_buyer_profile, src_main_resources_templates_buyer_purchases, src_main_resources_templates_account_edit [INFERRED 0.85]
- **Address save/delete flow** — src_main_resources_templates_checkout, src_main_resources_templates_addresses, src_main_resources_templates_account_addresses, route_post_checkout_save_address, route_post_checkout_delete_address [EXTRACTED 1.00]
- **Seller Hub pages sharing sidebar navigation** — src_main_resources_templates_seller_dashboard, src_main_resources_templates_seller_orders, src_main_resources_templates_seller_products, src_main_resources_templates_seller_profile [EXTRACTED 1.00]
- **Pages rendering product-card grids linking to /products/{id}** — src_main_resources_templates_search_results, src_main_resources_templates_shop_view, src_main_resources_templates_product_detail [EXTRACTED 1.00]
- **Seller product CRUD flow (list, add, edit, delete)** — src_main_resources_templates_seller_products, src_main_resources_templates_seller_product_add, src_main_resources_templates_seller_product_edit [EXTRACTED 1.00]

## Communities (28 total, 12 thin omitted)

### Community 0 - "Shared Utilities & Exceptions"
Cohesion: 0.06
Nodes (20): AccountController, AuthController, BuyerDashboardController, CategoryCatalogController, CheckoutController, ProductCatalogController, SellerOnboardingController, Buyer (+12 more)

### Community 1 - "DTO Validation"
Cohesion: 0.05
Nodes (8): PaymentController, PaymentRequestDTO, PaymentResponseDTO, PaymentMapper, Payment, PaymentRepository, PaymentService, PaymentServiceImpl

### Community 2 - "Address Management"
Cohesion: 0.07
Nodes (8): AddressController, AddressRequestDTO, AddressResponseDTO, AddressMapper, Address, AddressRepository, AddressService, AddressServiceImpl

### Community 3 - "Order Head API"
Cohesion: 0.07
Nodes (7): OrderHeadController, OrderHeadRequestDTO, OrderHeadResponseDTO, OrderHeadMapper, OrderHead, OrderHeadService, OrderHeadServiceImpl

### Community 4 - "Template Pages & Routes"
Cohesion: 0.06
Nodes (45): AccountController (likely), BuyerController (likely), CartController (likely), CategoryController (likely), CheckoutController (likely), Spring Boot error handling (likely), HomeController (likely), Model attribute account (+37 more)

### Community 5 - "Product Service"
Cohesion: 0.09
Nodes (7): ProductController, ProductRequestDTO, ProductResponseDTO, ProductMapper, Product, ProductRepository, ProductServiceImpl

### Community 6 - "Cart Item API"
Cohesion: 0.08
Nodes (8): CartItemController, CartItemRequestDTO, CartItemResponseDTO, CartItemMapper, CartItem, CartItemRepository, CartItemService, CartItemServiceImpl

### Community 7 - "Shared Template Fragments"
Cohesion: 0.08
Nodes (48): Model attribute cartCount, Model attribute globalCategories, Model attrs: order, items, payment, currentUser, param.success, Model attrs: product, recommendedProducts, Model attrs: keyword, shops, products, Model attr: seller (storeName, contactNumber, userAccount), Model attrs: totalSales, totalOrders, Model attrs: orderedItems (orderHead, product, orderQuantity, finalUnitPrice) (+40 more)

### Community 8 - "Order Item API"
Cohesion: 0.09
Nodes (7): OrderItemController, OrderItemRequestDTO, OrderItemResponseDTO, OrderItemMapper, OrderItem, OrderItemService, OrderItemServiceImpl

### Community 9 - "REST Controller Annotations"
Cohesion: 0.10
Nodes (7): SellerController, SellerRequestDTO, SellerResponseDTO, SellerMapper, Seller, SellerService, SellerServiceImpl

### Community 10 - "Category Module"
Cohesion: 0.10
Nodes (7): CategoryController, CategoryRequestDTO, CategoryResponseDTO, CategoryMapper, Category, CategoryRepository, CategoryServiceImpl

### Community 11 - "Payment API"
Cohesion: 0.12
Nodes (6): UserAccountController, UserAccountRequestDTO, UserAccountResponseDTO, UserAccountMapper, UserAccountService, UserAccountServiceImpl

### Community 12 - "Buyer Module"
Cohesion: 0.12
Nodes (6): BuyerController, BuyerRequestDTO, BuyerResponseDTO, BuyerMapper, BuyerService, BuyerServiceImpl

### Community 13 - "Cart Head API"
Cohesion: 0.12
Nodes (6): CartHeadController, CartHeadRequestDTO, CartHeadResponseDTO, CartHeadMapper, CartHeadService, CartHeadServiceImpl

### Community 14 - "Security & App Config"
Cohesion: 0.14
Nodes (3): CorsConfig, SecurityConfig, DatabaseSeeder

### Community 23 - "Frontend JS Modals"
Cohesion: 0.38
Nodes (4): closeLoginModal(), closeSignupModal(), openLoginModal(), openSignupModal()

## Knowledge Gaps
- **45 isolated node(s):** `com.abaca:Abaca-Ecommerce`, `Route POST/GET /search`, `Route /cart`, `Route /checkout`, `Route POST /cart/remove` (+40 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 176 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **12 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `UserAccount` connect `Account & Registration` to `Shared Utilities & Exceptions`, `Address Management`, `REST Controller Annotations`, `Payment API`, `Buyer Module`, `User Details Auth`, `Custom User Details`, `Profile Images`?**
  _High betweenness centrality (0.055) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `UserAccount` (e.g. with `.processRegistration()` and `.initDatabase()`) actually correct?**
  _`UserAccount` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `com.abaca:Abaca-Ecommerce`, `Route POST/GET /search`, `Route /cart` to the rest of the system?**
  _45 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Shared Utilities & Exceptions` be split into smaller, more focused modules?**
  _Cohesion score 0.05767741935483871 - nodes in this community are weakly interconnected._
- **Why does `CustomUserDetails` connect `Custom User Details` to `Shared Utilities & Exceptions`, `Address Management`, `Account & Registration`, `Seller Dashboard`, `Checkout & Account Pages`, `User Details Auth`, `Repositories & Queries`, `Global Controller Advice`, `Profile Images`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **Should `DTO Validation` be split into smaller, more focused modules?**
  _Cohesion score 0.05438184663536776 - nodes in this community are weakly interconnected._
- **Why does `OrderHead` connect `Order Head API` to `Shared Utilities & Exceptions`, `DTO Validation`, `Address Management`, `Order Item API`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._