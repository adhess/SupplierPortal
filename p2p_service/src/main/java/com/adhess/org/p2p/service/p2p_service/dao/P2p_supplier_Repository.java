package com.adhess.org.p2p.service.p2p_service.dao;

import com.adhess.org.p2p.service.p2p_service.entities.P2p_supplier;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface P2p_supplier_Repository extends MongoRepository<P2p_supplier, String> {
    public P2p_supplier findBy_id(String id);
}
