package com.example;

import com.example.controller.ClientAuth;
import com.example.controller.ClientProduct;
import com.example.controller.ClientRev;
import com.example.model.JWT;
import com.example.model.Product;
import com.example.model.Review;
import com.example.repository.JWTRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ReviewCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewCApplication.class, args);
    }
    @Autowired
    ClientAuth clientAuth;
    @Autowired
    ClientRev clientRev;

    @Autowired
    ClientProduct clientProduct;


    @Bean
    public CommandLineRunner loadData(JWTRepository repository, ReviewRepository reviewRepository, ProductRepository repositoryPro) {
        return (args) -> {
            // save a couple of customers
            if(repository.count() == 0){
                List<JWT> lista = clientAuth.send();
                if (lista != null){
                    repository.saveAll(lista);
                }
            }
            if(reviewRepository.count() == 0){
                List<Review> lista = clientRev.send();
                if (lista != null){
                    reviewRepository.saveAll(lista);
                }
            }
            if(repositoryPro.count() == 0){
                List<Product> lista = clientProduct.send();
                if (lista != null){
                    repositoryPro.saveAll(lista);
                }
            }
            //repository.save(new Customer("Jack", "Bauer"));
        };

    }
}
