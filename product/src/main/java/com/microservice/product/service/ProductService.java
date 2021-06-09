package com.microservice.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.product.VO.ResponseTemplateVO;
import com.microservice.product.VO.Store;
import com.microservice.product.entity.Product;
import com.microservice.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public ResponseTemplateVO getProductById(Long id) {	
		ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
		Product product = productRepository.findById(id).get();
		responseTemplateVO.setProduct(product);		
		Store store = restTemplate.getForObject("http://STORE-SERVICE/stores/"+product.getStoreId(), Store.class);
		responseTemplateVO.setStore(store);
		return responseTemplateVO;
	}
}
