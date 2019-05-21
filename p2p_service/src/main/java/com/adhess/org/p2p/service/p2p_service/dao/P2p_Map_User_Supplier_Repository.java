package com.adhess.org.p2p.service.p2p_service.dao;

import com.adhess.org.p2p.service.p2p_service.entities.MapUserSupplier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface P2p_Map_User_Supplier_Repository extends MongoRepository<MapUserSupplier, String> {
    MapUserSupplier findByUsername(String username);
}
