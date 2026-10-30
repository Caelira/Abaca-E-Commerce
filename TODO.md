# TODO

## 1. Fix first (bugs and risks)
- [ ] Check `cart.html`, `checkout.html`, `buyer-purchases.html`, `category-view.html` (git log says deleted, but they are in the clone)
- [ ] Repair `order-detail.html` (reportedly two full HTML documents back to back)
- [ ] Make `start-selling.html` call the layout like every other page
- [ ] Move the DB password out of `application.properties` (env vars / gitignored profile)
- [ ] Replace `ddl-auto=update` with Flyway/Liquibase; turn off `show-sql` outside dev
- [ ] Review `SecurityConfig` / `CorsConfig`: `/api/v1/**` is open to any logged-in user
- [ ] Remove or replace `calculateMockShippingFee()` in checkout

## 2. Quality
- [ ] Tests: services (checkout, stock deduction, cart), repositories, security rules
- [ ] `@RestControllerAdvice` with proper error responses and a friendlier `error.html`
- [ ] Make checkout transactional end to end; test out-of-stock cases
- [ ] Validate input and limit file uploads (size, type)
- [ ] Pagination and indexes for product feed and search

## 3. Front end
- [ ] Extract duplicated markup (product cards, seller sidebar) into fragments
- [ ] Unify the "Seller Centre" and "Seller Hub" sidebars
- [ ] Review mobile layout and accessibility (labels, alt text, contrast)
- [ ] Split `style.css` / `main.js` if large

## 4. Features
- [ ] Sales analytics report for admins (verify if it exists)
- [ ] Abandoned-cart cleanup job (verify if it exists)
- [ ] Admin role, order status tracking, real payment and shipping integration
- [ ] Product reviews, wishlist

## 5. Housekeeping
- [ ] Verify the README "What It Does" section against the code
- [ ] Add LICENSE and screenshots
- [ ] Remove or fill the empty `skills/` placeholders
- [ ] CI (GitHub Actions `./mvnw verify`), Dockerfile + compose for Postgres
- [ ] Re-run `/graphify . --update` to redo the 13 partly-read templates
