package com.adhess.org.p2p.service.p2p_service.dao;

import com.adhess.org.p2p.service.p2p_service.entities.P2p_invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface P2p_invoice_Repository extends MongoRepository<P2p_invoice, String>  {
    @Query(value = "{}", count = true)
    public int countAll();

    Page<P2p_invoice> findAllByRoot_SERCPTTIE(Long sercpttie, Pageable page);
    int countAllByRoot_SERCPTTIE(Long sercpttie);

}