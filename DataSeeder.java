package com.mabentech.config;

import com.mabentech.model.Product;
import com.mabentech.model.User;
import com.mabentech.repository.ProductRepository;
import com.mabentech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdminUser();
        seedProducts();
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail("admin@mabentech.com")) {
            userRepository.save(User.builder()
                    .firstName("MABENTECH").lastName("Admin")
                    .email("admin@mabentech.com")
                    .password(passwordEncoder.encode("Admin@2025!"))
                    .role(User.Role.ADMIN).enabled(true).emailVerified(true)
                    .country("Global").currency("USD")
                    .build());
        }
    }

    private void seedProducts() {
        if (productRepository.count() > 0) return;

        List<Product> products = List.of(
            // ── LAPTOPS ──────────────────────────────────────────────────
            Product.builder().name("MacBook Pro 16\" M3 Max").brand("Apple").model("MUW73LL/A")
                .category("laptop").subcategory("workstation")
                .description("The most powerful MacBook ever. Apple M3 Max chip, 40-core GPU, 48GB unified memory, 1TB SSD. Perfect for pro video editors and developers worldwide.")
                .price(new BigDecimal("3999.00")).oldPrice(new BigDecimal("4299.00"))
                .stock(25).rating(4.9).reviewCount(1842).badge("BESTSELLER").featured(true)
                .imageUrl("/images/macbook-pro-m3.svg")
                .specs("{\"processor\":\"Apple M3 Max\",\"ram\":\"48GB\",\"storage\":\"1TB SSD\",\"display\":\"16.2-inch Liquid Retina XDR\",\"battery\":\"22hr\",\"os\":\"macOS Sonoma\",\"weight\":\"2.14kg\"}").build(),

            Product.builder().name("Dell XPS 15 OLED").brand("Dell").model("XPS9530-7659BLK")
                .category("laptop").subcategory("ultrabook")
                .description("Stunning 3.5K OLED display with Intel Core i9-13900H, RTX 4070, 32GB RAM. Ultimate thin-and-powerful laptop for creators.")
                .price(new BigDecimal("2499.00")).oldPrice(new BigDecimal("2799.00"))
                .stock(18).rating(4.8).reviewCount(967).badge("HOT").featured(true)
                .imageUrl("/images/dell-xps-15.svg")
                .specs("{\"processor\":\"Intel Core i9-13900H\",\"ram\":\"32GB DDR5\",\"storage\":\"1TB SSD\",\"display\":\"15.6-inch 3.5K OLED\",\"gpu\":\"RTX 4070 8GB\",\"battery\":\"13hr\",\"os\":\"Windows 11 Pro\"}").build(),

            Product.builder().name("ASUS ROG Zephyrus G16").brand("ASUS").model("GA605WV-QR030W")
                .category("laptop").subcategory("gaming")
                .description("AMD Ryzen AI 9 HX 370, RTX 4090, 240Hz 2.5K OLED display. The most portable gaming powerhouse on Earth.")
                .price(new BigDecimal("3299.00")).oldPrice(null)
                .stock(12).rating(4.9).reviewCount(543).badge("NEW").featured(true)
                .imageUrl("/images/asus-rog.svg")
                .specs("{\"processor\":\"AMD Ryzen AI 9 HX 370\",\"ram\":\"32GB DDR5\",\"storage\":\"2TB SSD\",\"display\":\"16-inch 2.5K 240Hz OLED\",\"gpu\":\"RTX 4090 16GB\",\"battery\":\"10hr\",\"os\":\"Windows 11 Home\"}").build(),

            Product.builder().name("Lenovo ThinkPad X1 Carbon Gen 12").brand("Lenovo").model("21KC0057US")
                .category("laptop").subcategory("business")
                .description("Intel Core Ultra 7 165U, 32GB LPDDR5x, 1TB SSD. The world's lightest 14-inch business laptop. MIL-SPEC tested.")
                .price(new BigDecimal("1899.00")).oldPrice(new BigDecimal("2099.00"))
                .stock(30).rating(4.8).reviewCount(2104).badge("BESTSELLER").featured(true)
                .imageUrl("/images/thinkpad-x1.svg")
                .specs("{\"processor\":\"Intel Core Ultra 7 165U\",\"ram\":\"32GB LPDDR5x\",\"storage\":\"1TB SSD\",\"display\":\"14-inch 2.8K OLED\",\"battery\":\"15hr\",\"weight\":\"1.12kg\",\"os\":\"Windows 11 Pro\"}").build(),

            Product.builder().name("HP Spectre x360 14").brand("HP").model("14-ef2013dx")
                .category("laptop").subcategory("ultrabook")
                .description("Intel Core Ultra 7, 32GB RAM, 2TB SSD, OLED touch display. 2-in-1 convertible with HP Tilt Pen included.")
                .price(new BigDecimal("1699.00")).oldPrice(new BigDecimal("1899.00"))
                .stock(22).rating(4.7).reviewCount(734).badge("SALE").featured(false)
                .imageUrl("/images/hp-spectre.svg")
                .specs("{\"processor\":\"Intel Core Ultra 7 155H\",\"ram\":\"32GB\",\"storage\":\"2TB SSD\",\"display\":\"14-inch 2.8K OLED Touch\",\"battery\":\"17hr\",\"os\":\"Windows 11 Home\"}").build(),

            Product.builder().name("Microsoft Surface Laptop 6").brand("Microsoft").model("ZJQ-00001")
                .category("laptop").subcategory("ultrabook")
                .description("Snapdragon X Elite, 64GB RAM, 1TB SSD. Blazing-fast Copilot+ PC with all-day battery life and stunning display.")
                .price(new BigDecimal("2499.00")).oldPrice(null)
                .stock(15).rating(4.8).reviewCount(312).badge("NEW").featured(false)
                .imageUrl("/images/surface-laptop.svg")
                .specs("{\"processor\":\"Snapdragon X Elite\",\"ram\":\"64GB\",\"storage\":\"1TB SSD\",\"display\":\"15-inch PixelSense\",\"battery\":\"22hr\",\"os\":\"Windows 11 Home\"}").build(),

            Product.builder().name("Acer Predator Helios Neo 18").brand("Acer").model("PHN18-71-97R0")
                .category("laptop").subcategory("gaming")
                .description("Intel Core i9-14900HX, RTX 4080, 165Hz IPS display, 32GB DDR5. Dominate every game at max settings.")
                .price(new BigDecimal("1999.00")).oldPrice(new BigDecimal("2299.00"))
                .stock(10).rating(4.7).reviewCount(489).badge("SALE").featured(false)
                .imageUrl("/images/acer-predator.svg")
                .specs("{\"processor\":\"Intel Core i9-14900HX\",\"ram\":\"32GB DDR5\",\"storage\":\"1TB SSD\",\"display\":\"18-inch QHD 165Hz IPS\",\"gpu\":\"RTX 4080 12GB\",\"os\":\"Windows 11 Home\"}").build(),

            Product.builder().name("MacBook Air 13\" M3").brand("Apple").model("MRXN3LL/A")
                .category("laptop").subcategory("ultrabook")
                .description("Impossibly thin, all-day battery. Apple M3 chip, 16GB RAM, 512GB SSD. The world's best-selling laptop, reinvented.")
                .price(new BigDecimal("1299.00")).oldPrice(null)
                .stock(50).rating(4.9).reviewCount(3561).badge("BESTSELLER").featured(true)
                .imageUrl("/images/macbook-air-m3.svg")
                .specs("{\"processor\":\"Apple M3\",\"ram\":\"16GB\",\"storage\":\"512GB SSD\",\"display\":\"13.6-inch Liquid Retina\",\"battery\":\"18hr\",\"weight\":\"1.24kg\",\"os\":\"macOS Sonoma\"}").build(),

            // ── ACCESSORIES ──────────────────────────────────────────────
            Product.builder().name("Logitech MX Keys S").brand("Logitech").model("920-011587")
                .category("accessory").subcategory("keyboard")
                .description("Advanced wireless keyboard with Smart Backlighting, Multi-OS support, and seamless multi-device workflow. Type on up to 3 devices.")
                .price(new BigDecimal("119.00")).oldPrice(new BigDecimal("139.00"))
                .stock(80).rating(4.8).reviewCount(2341).badge("BESTSELLER").featured(true)
                .imageUrl("/images/mx-keys.svg")
                .specs("{\"connectivity\":\"Bluetooth/USB-C\",\"battery\":\"10 days backlit, 5 months no backlight\",\"compatibility\":\"Windows, macOS, Linux, iOS, Android\",\"layout\":\"Full-size with numpad\"}").build(),

            Product.builder().name("Logitech MX Master 3S").brand("Logitech").model("910-006557")
                .category("accessory").subcategory("mouse")
                .description("8K DPI precision sensor, ultra-fast MagSpeed scroll, quiet clicks. The master of all mice for power users.")
                .price(new BigDecimal("99.00")).oldPrice(null)
                .stock(95).rating(4.9).reviewCount(4230).badge("HOT").featured(true)
                .imageUrl("/images/mx-master.svg")
                .specs("{\"dpi\":\"200–8000 DPI\",\"battery\":\"70 days\",\"connectivity\":\"Bluetooth/USB-C\",\"compatibility\":\"Windows, macOS, Linux\"}").build(),

            Product.builder().name("CalDigit TS4 Thunderbolt 4 Dock").brand("CalDigit").model("TS4-GREY")
                .category("accessory").subcategory("dock")
                .description("18 ports, 98W charging, Thunderbolt 4/USB4. One cable to rule them all — power, data, and dual 6K displays.")
                .price(new BigDecimal("349.00")).oldPrice(new BigDecimal("399.00"))
                .stock(35).rating(4.8).reviewCount(876).badge("HOT").featured(false)
                .imageUrl("/images/caldigit-ts4.svg")
                .specs("{\"ports\":\"18 total: 3x Thunderbolt 4, 5x USB-A, 3x USB-C, SD 4.0, microSD, 2.5GbE, 3.5mm\",\"power\":\"98W host charging\",\"displays\":\"Dual 6K\"}").build(),

            Product.builder().name("Samsung T9 Portable SSD 4TB").brand("Samsung").model("MU-PG4T0B")
                .category("accessory").subcategory("storage")
                .description("2,000 MB/s read/write, IP65 water & dust resistant, AES 256-bit hardware encryption. Take 4TB everywhere.")
                .price(new BigDecimal("289.00")).oldPrice(new BigDecimal("349.00"))
                .stock(45).rating(4.7).reviewCount(654).badge("SALE").featured(false)
                .imageUrl("/images/samsung-t9.svg")
                .specs("{\"capacity\":\"4TB\",\"read\":\"2,000 MB/s\",\"write\":\"2,000 MB/s\",\"interface\":\"USB 3.2 Gen 2x2\",\"protection\":\"IP65, AES 256-bit\"}").build(),

            Product.builder().name("Keychron Q1 Pro Wireless Keyboard").brand("Keychron").model("Q1P-M3")
                .category("accessory").subcategory("keyboard")
                .description("QMK/VIA wireless mechanical keyboard with gasket mount, POM plate, and hot-swappable switches. 75% layout perfection.")
                .price(new BigDecimal("199.00")).oldPrice(null)
                .stock(28).rating(4.8).reviewCount(412).badge("NEW").featured(false)
                .imageUrl("/images/keychron-q1.svg")
                .specs("{\"layout\":\"75%\",\"switches\":\"Hot-swappable\",\"connectivity\":\"Bluetooth 5.1/USB-C\",\"battery\":\"4000mAh\",\"backlight\":\"South-facing RGB\"}").build(),

            Product.builder().name("LG 32UN880-B UltraFine Ergo Monitor").brand("LG").model("32UN880-B")
                .category("accessory").subcategory("monitor")
                .description("32-inch 4K UHD IPS, ergonomic arm stand, USB-C 96W PD, HDR10. The ultimate laptop companion monitor.")
                .price(new BigDecimal("699.00")).oldPrice(new BigDecimal("799.00"))
                .stock(20).rating(4.7).reviewCount(548).badge("SALE").featured(true)
                .imageUrl("/images/lg-monitor.svg")
                .specs("{\"display\":\"32-inch 4K UHD IPS\",\"refresh\":\"60Hz\",\"hdr\":\"HDR10\",\"ports\":\"USB-C 96W, HDMI, DisplayPort, USB-A\",\"stand\":\"Full ergonomic arm\"}").build(),

            Product.builder().name("Moshi Slim Carry Case 15\"").brand("Moshi").model("99MO117001")
                .category("accessory").subcategory("bag")
                .description("Premium vegan leather laptop sleeve with magnetic closure. Fits 15-16 inch laptops. PETA-approved, eco-friendly materials.")
                .price(new BigDecimal("79.00")).oldPrice(null)
                .stock(60).rating(4.6).reviewCount(287).badge("NEW").featured(false)
                .imageUrl("/images/moshi-case.svg")
                .specs("{\"compatibility\":\"13-16 inch laptops\",\"material\":\"Vegan leather\",\"closure\":\"Magnetic\",\"weight\":\"340g\"}").build(),

            Product.builder().name("Anker 200W GaN Charger").brand("Anker").model("A2346")
                .category("accessory").subcategory("charger")
                .description("200W GaN III technology, 4 ports (2x USB-C, 2x USB-A), charges MacBook Pro + iPad + iPhone simultaneously.")
                .price(new BigDecimal("89.00")).oldPrice(new BigDecimal("109.00"))
                .stock(70).rating(4.8).reviewCount(1923).badge("BESTSELLER").featured(false)
                .imageUrl("/images/anker-charger.svg")
                .specs("{\"total_power\":\"200W\",\"ports\":\"2x USB-C (140W max), 2x USB-A (12W)\",\"technology\":\"GaN III\",\"compatibility\":\"Universal\"}").build()
        );

        productRepository.saveAll(products);
        System.out.println("✅ Seeded " + products.size() + " MABENTECH products");
    }
}
