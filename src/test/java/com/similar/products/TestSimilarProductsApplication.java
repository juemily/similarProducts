package com.similar.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSimilarProductsApplication {

	public static void main(String[] args) {
		SpringApplication.from(SimilarProductsApplication::main).with(TestSimilarProductsApplication.class).run(args);
	}

}
