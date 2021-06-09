package com.microservice.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.store.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

}
