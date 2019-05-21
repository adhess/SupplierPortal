package com.adhess.org.p2p.service.p2p_service.service.API;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_Map_User_Supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.entities.MapUserSupplier;
import com.adhess.org.p2p.service.p2p_service.model.AffectSupplierModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class Supplier_api {

    @Autowired
    private P2p_supplier_Repository p2p_supplier_repository;

    @Autowired
    private P2p_Map_User_Supplier_Repository mapper_repository;

    @RequestMapping(value = "admin/getAllSupplierSRM", method = RequestMethod.GET)
    public List getSuppliers() throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return p2p_supplier_repository.findAll();
    }

    @RequestMapping(value = "admin/affectSupplierToSupplierSRM", method = RequestMethod.POST)
    public MapUserSupplier affectSupplierToSupplierSRM(@RequestBody AffectSupplierModel reg) {
        MapUserSupplier map = mapper_repository.findByUsername(reg.getUsername());
        if (map == null) map = new MapUserSupplier(reg.getUsername(), reg.getId());
        else map.setId_supplier(reg.getId());
        return this.mapper_repository.save(map);
    }
}
