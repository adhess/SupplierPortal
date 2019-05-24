package com.adhess.org.p2p.service.p2p_service.dao;

import com.adhess.org.p2p.service.p2p_service.entities.P2p_order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.List;


public interface P2p_order_Repository extends MongoRepository<P2p_order, String>{
    @Query(value = "{}", count = true)
    public int countAll();


    public int countAllByRoot_STATUS(String status);
    public int countAllByRoot_STATUSAndRoot_SERCPTTIE(String status,Long sercpttie);

    int countAllByRoot_SERCPTTIE(Long sercpttie);
    Page<P2p_order> findAllByRoot_SERCPTTIE(Long sercpttie, PageRequest pageRequest);
}