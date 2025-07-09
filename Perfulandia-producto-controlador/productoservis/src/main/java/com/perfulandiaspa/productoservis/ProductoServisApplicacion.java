package com.perfulandiaspa.productoservis;
import com.perfulandiaspa.productoservis.model.Product;
import com.perfulandiaspa.productoservis.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductoServisApplicacion {

    public static void main(String[] args) {
        SpringApplication.run(ProductoServisApplicacion.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                System.out.println("Inicializando datos de productos...");
                productRepository.save(new Product(null, "Polo Blue", 120.00, 50, "Perfume Polo blue 100ML"));
                productRepository.save(new Product(null, "Tom Ford", 200.00, 10, "Perfume Tom Ford 100ML"));
                productRepository.save(new Product(null, "Tommy Hilfiger'", 50.00, 30, "Perfume Tommy 100ML"));
                System.out.println("Datos de productos inicializados.");
            }
        };
    }
}