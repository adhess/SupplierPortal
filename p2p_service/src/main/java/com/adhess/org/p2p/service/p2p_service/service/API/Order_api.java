package com.adhess.org.p2p.service.p2p_service.service.API;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_order_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.entities.P2p_order;
import com.adhess.org.p2p.service.p2p_service.service.AuthenticatedHelper;
import com.adhess.org.p2p.service.p2p_service.service.P2P_Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
public class Order_api {
    @Autowired
    private P2p_order_Repository p2p_order_repository;

    @Autowired
    private P2p_supplier_Repository p2p_supplier_repository;

    @RequestMapping(value = "auth/order/pages/{sizeperpage}", method = RequestMethod.GET)
    public int countPages(@PathVariable int sizeperpage) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return P2P_Helper.countPages(sizeperpage, countOrders());
    }

    private int countOrders() {
        int count = 0;
        if (AuthenticatedHelper.isAdmin()) {
            count = p2p_order_repository.countAll();
        } else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code = P2P_Helper.getCodeByUsername(username);
            count = p2p_order_repository.countAllByRoot_SERCPTTIE(code);
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isOrderRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                count = p2p_order_repository.countAllByRoot_SERCPTTIE(code);
            } else throw new RuntimeException("User not allowed to read resources exception...");
        } else throw new RuntimeException("User not allowed exception...");
        return count;
    }

    @RequestMapping(value = "auth/order/{page}/{size}", method = RequestMethod.GET)
    public Page<P2p_order> getOrders(@PathVariable int page, @PathVariable int size) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (AuthenticatedHelper.isAdmin())
            return p2p_order_repository.findAll(new PageRequest(page, size));
        else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code = P2P_Helper.getCodeByUsername(username);
            return this.p2p_order_repository.findAllByRoot_SERCPTTIE(code, new PageRequest(page, size));
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isOrderRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                return this.p2p_order_repository.findAllByRoot_SERCPTTIE(code, new PageRequest(page, size));
            } else throw new RuntimeException("User not allowed to read resources exception...");
        }
        throw new RuntimeException("User not allowed exception...");
    }

}
