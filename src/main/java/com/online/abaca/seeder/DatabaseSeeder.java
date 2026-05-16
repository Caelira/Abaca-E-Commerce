package com.online.abaca.seeder;

import com.online.abaca.model.*;
import com.online.abaca.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(
            UserAccountRepository userRepo,
            AddressRepository addressRepo,
            SellerRepository sellerRepo,
            BuyerRepository buyerRepo,
            CategoryRepository categoryRepo,
            ProductRepository productRepo) {

        return args -> {
            if (userRepo.count() > 0) {
                log.info("Database already seeded. Skipping initialization.");
                return;
            }

            log.info("Seeding database with Albay Abaca Artisan data...");

            String defaultPassword = passwordEncoder.encode("Password123!");

            // --- USERS ---
            UserAccount sellerUser1 = new UserAccount();
            sellerUser1.setEmail("daraga.handicrafts@gmail.com");
            sellerUser1.setPassword(defaultPassword);
            sellerUser1.setRole("SELLER");
            sellerUser1.setCreatedAt(LocalDateTime.now());

            UserAccount sellerUser2 = new UserAccount();
            sellerUser2.setEmail("camalig.weavers@yahoo.com");
            sellerUser2.setPassword(defaultPassword);
            sellerUser2.setRole("SELLER");
            sellerUser2.setCreatedAt(LocalDateTime.now());

            UserAccount buyerUser1 = new UserAccount();
            buyerUser1.setEmail("juan.delacruz@gmail.com");
            buyerUser1.setPassword(defaultPassword);
            buyerUser1.setRole("BUYER");
            buyerUser1.setCreatedAt(LocalDateTime.now());

            userRepo.saveAll(List.of(sellerUser1, sellerUser2, buyerUser1));

            // --- ADDRESSES ---
            Address addr1 = new Address();
            addr1.setUserAccount(sellerUser1);
            addr1.setAddressType("Shop");
            addr1.setStreet("Market Site");
            addr1.setBrgy("Ilawod");
            addr1.setMunicipality("Daraga");
            addr1.setProvince("Albay");

            Address addr2 = new Address();
            addr2.setUserAccount(sellerUser2);
            addr2.setAddressType("Workshop");
            addr2.setStreet("National Highway");
            addr2.setBrgy("Cabangan");
            addr2.setMunicipality("Camalig");
            addr2.setProvince("Albay");

            Address addr3 = new Address();
            addr3.setUserAccount(buyerUser1);
            addr3.setAddressType("Home");
            addr3.setStreet("143 Rizal Street");
            addr3.setBrgy("Bitano");
            addr3.setMunicipality("Legazpi City");
            addr3.setProvince("Albay");

            addressRepo.saveAll(List.of(addr1, addr2, addr3));

            // --- SELLERS & BUYERS ---
            Seller seller1 = new Seller();
            seller1.setUserAccount(sellerUser1);
            seller1.setStoreName("Daraga Native Handicrafts");
            seller1.setContactNumber("09171234567");

            Seller seller2 = new Seller();
            seller2.setUserAccount(sellerUser2);
            seller2.setStoreName("Camalig Pinukpok Creations");
            seller2.setContactNumber("09189876543");

            sellerRepo.saveAll(List.of(seller1, seller2));

            Buyer buyer1 = new Buyer();
            buyer1.setUserAccount(buyerUser1);
            buyer1.setContactNumber("09991112233");

            buyerRepo.save(buyer1);

            // --- CATEGORIES ---
            Category catRaw = new Category();
            catRaw.setCategoryName("Raw Textiles");
            catRaw.setDescription("Unprocessed Sinamay and Pinukpok fibers by the meter.");

            Category catBags = new Category();
            catBags.setCategoryName("Bags & Accessories");
            catBags.setDescription("Handwoven Abaca tote bags, slings, and purses.");

            Category catDecor = new Category();
            catDecor.setCategoryName("Home & Living");
            catDecor.setDescription("Abaca rugs, placemats, and native wall decors.");

            categoryRepo.saveAll(List.of(catRaw, catBags, catDecor));

            // --- INITIAL PRODUCTS ---
            List<Product> productsToSave = new ArrayList<>();

            Product p1 = new Product();
            p1.setSeller(seller1);
            p1.setCategory(catBags);
            p1.setProductName("Classic Abaca Tote Bag with Leather Straps");
            p1.setProductPrice(new BigDecimal("450.00"));
            p1.setOriginalPrice(new BigDecimal("600.00"));
            p1.setStockQuantity(50);
            p1.setCreatedAt(LocalDateTime.now().minusDays(2));
            p1.setTotalSold(1250);
            productsToSave.add(p1);

            Product p2 = new Product();
            p2.setSeller(seller1);
            p2.setCategory(catDecor);
            p2.setProductName("Round Sinamay Area Rug (1.5 Meters)");
            p2.setProductPrice(new BigDecimal("1200.00"));
            p2.setOriginalPrice(null);
            p2.setStockQuantity(15);
            p2.setCreatedAt(LocalDateTime.now().minusDays(5));
            p2.setTotalSold(45);
            productsToSave.add(p2);

            Product p3 = new Product();
            p3.setSeller(seller2);
            p3.setCategory(catRaw);
            p3.setProductName("Premium Pinukpok Fabric (Per Meter)");
            p3.setProductPrice(new BigDecimal("250.00"));
            p3.setOriginalPrice(new BigDecimal("300.00"));
            p3.setStockQuantity(100);
            p3.setCreatedAt(LocalDateTime.now().minusHours(4));
            p3.setTotalSold(8900);
            productsToSave.add(p3);

            // --- GENERATE 297 MOCK PRODUCTS ---
            log.info("Generating 297 additional bulk products...");
            Random random = new Random();

            String[] adjectives = {"Handwoven", "Authentic", "Premium", "Eco-friendly", "Rustic", "Export-Quality", "Traditional", "Native"};
            String[] materials = {"Abaca", "Sinamay", "Pinukpok", "Rattan-Accented", "Buri-Blend"};
            String[] items = {"Sling Bag", "Placemat (Set of 4)", "Coin Purse", "Wall Decor", "Storage Basket", "Slippers", "Table Runner", "Sun Hat"};

            List<Seller> sellers = List.of(seller1, seller2);
            List<Category> categories = List.of(catRaw, catBags, catDecor);

            for (int i = 0; i < 297; i++) {
                Product p = new Product();
                p.setSeller(sellers.get(random.nextInt(sellers.size())));
                p.setCategory(categories.get(random.nextInt(categories.size())));

                 String name = adjectives[random.nextInt(adjectives.length)] + " " +
                        materials[random.nextInt(materials.length)] + " " +
                        items[random.nextInt(items.length)];
                p.setProductName(name + " - Variant " + (i + 1));

                 double basePrice = 100 + (random.nextDouble() * 1400);
                p.setProductPrice(BigDecimal.valueOf(basePrice).setScale(2, RoundingMode.HALF_UP));

                 if (random.nextBoolean()) {
                    double markup = 1.1 + (random.nextDouble() * 0.3);
                    p.setOriginalPrice(BigDecimal.valueOf(basePrice * markup).setScale(2, RoundingMode.HALF_UP));
                } else {
                    p.setOriginalPrice(null);
                }

                p.setStockQuantity(random.nextInt(200) + 5); // 5 to 204 stock
                p.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(60))); // Up to 2 months old
                p.setTotalSold(random.nextInt(3000)); // 0 to 2999 sold

                productsToSave.add(p);
            }

            // Save all 300n
            productRepo.saveAll(productsToSave);

            log.info("Database seeding completed successfully. 300 products loaded.");
        };
    }
}