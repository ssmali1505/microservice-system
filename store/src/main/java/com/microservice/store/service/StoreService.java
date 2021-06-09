package com.microservice.store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.store.entity.Store;
import com.microservice.store.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;

	public Store saveStore(Store store) {
		return storeRepository.save(store);
	}

	public Optional<Store> getStoreById(Long id) {		
		return storeRepository.findById(id);
	}
}
