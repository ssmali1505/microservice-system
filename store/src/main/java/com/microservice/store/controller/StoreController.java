package com.microservice.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.store.entity.Store;
import com.microservice.store.service.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@PostMapping("/")
	public Store CreateStore(@RequestBody Store store) {
		return storeService.saveStore(store);
	}
	
	@GetMapping("/{id}")
	public Optional<Store> getStoreById(@PathVariable("id") Long id) {		
		return storeService.getStoreById(id);
	}
	
}
