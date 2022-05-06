package com.example.kimcoffee.repository;

import com.example.kimcoffee.model.Category;
import com.example.kimcoffee.model.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository productRepository;

    private final Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있음")
    void 상품_추가() {
        productRepository.insert(newProduct);
        List<Product> list = productRepository.findAll();
        assertThat(list.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있음")
    void 이름_상품_조회() {
        Optional<Product> product = productRepository.findByName(newProduct.getProductName());
        assertThat(product.get().getProductId(),equalTo(newProduct.getProductId()));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디으로 조회할 수 있음")
    void 아이디_상품_조회() {
        Optional<Product> product = productRepository.findById(newProduct.getProductId());
        assertThat(product.get().getProductId(),equalTo(newProduct.getProductId()));
    }

    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있음")
    void 카테고리_상품_조회() {
        List<Product> products = productRepository.findByCategory(newProduct.getCategory());
        assertThat(products.isEmpty() , is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있음")
    void 상품_수정() {
        newProduct.setProductName("update-product");
        productRepository.update(newProduct);

        Optional<Product> product = productRepository.findById(newProduct.getProductId());
        assertThat(product.isEmpty() , is(false));
        assertThat(product.get() , samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(6)
    @DisplayName("상품전체를 삭제할 수 있음")
    void 상품_삭제() {
        productRepository.deleteAll();
        List<Product> products = productRepository.findAll();
        assertThat(products.isEmpty(), is(true));

    }
}