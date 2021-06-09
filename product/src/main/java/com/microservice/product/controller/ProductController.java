package com.microservice.product.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.product.VO.ResponseTemplateVO;
import com.microservice.product.entity.Product;
import com.microservice.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	public Product CreateProduct(@RequestBody(required=true) Product product) {
		return productService.saveProduct(product);
	}
	
	@GetMapping("/{id}")
	public ResponseTemplateVO getProductById(@PathVariable("id") Long id) {		
		return productService.getProductById(id);
	}
	
}
