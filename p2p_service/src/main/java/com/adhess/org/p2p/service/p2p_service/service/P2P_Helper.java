package com.adhess.org.p2p.service.p2p_service.service;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_Map_User_Supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.entities.MapUserSupplier;
import com.adhess.org.p2p.service.p2p_service.entities.P2p_supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Service
public class P2P_Helper {

    private static P2p_Map_User_Supplier_Repository mapper_repository;

    private static P2p_supplier_Repository p2p_supplier_repository;

    @Autowired
    public P2P_Helper(P2p_Map_User_Supplier_Repository mapper_repository,
                      P2p_supplier_Repository p2p_supplier_repository) {
        this.mapper_repository = mapper_repository;
        this.p2p_supplier_repository = p2p_supplier_repository;
    }

    public static int countPages(int sizePerPage, int total) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        int pages = total / sizePerPage;
        if (!(pages * sizePerPage == total)) pages++;
        return pages;
    }
    public static Long getCodeByUsername(String username) {
        MapUserSupplier map = mapper_repository.findByUsername(username);
        if (map == null) throw new RuntimeException("supplier not mapped yet exception...");
        P2p_supplier supplier_srm = p2p_supplier_repository.findBy_id(map.getId_supplier());
        if (supplier_srm == null) throw new RuntimeException("supplier id exception...");
        return supplier_srm.getRoot().getSERCPTTIE();
    }
}
