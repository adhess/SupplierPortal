package com.adhess.org.p2p.service.p2p_service.service.API;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_Map_User_Supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_invoice_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.entities.P2p_invoice;
import com.adhess.org.p2p.service.p2p_service.service.AuthenticatedHelper;
import com.adhess.org.p2p.service.p2p_service.service.P2P_Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
public class Invoice_api {
    @Autowired
    private P2p_invoice_Repository p2p_invoice_repository;

    @Autowired
    private P2p_Map_User_Supplier_Repository mapper_repository;

    @Autowired
    private P2p_supplier_Repository p2p_supplier_repository;


    @RequestMapping(value = "auth/invoice/pages/{sizeperpage}", method = RequestMethod.GET)
    public int countPages(@PathVariable int sizeperpage) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        return P2P_Helper.countPages(sizeperpage, countInvoices());
    }

    private int countInvoices() {
        int count = 0;
        if (AuthenticatedHelper.isAdmin()) {
            count = p2p_invoice_repository.countAll();

        } else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code1 = P2P_Helper.getCodeByUsername(username);
            count = p2p_invoice_repository.countAllByRoot_SERCPTTIE(code1);
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isInvoiceRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                count = p2p_invoice_repository.countAllByRoot_SERCPTTIE(code);
            } else throw new RuntimeException("User not allowed to read resources exception...");
        } else throw new RuntimeException("User not allowed exception...");
        return count;
    }




    @RequestMapping(value = "auth/invoice/{page}/{size}", method = RequestMethod.GET)
    public Page<P2p_invoice> getOrders(@PathVariable int page, @PathVariable int size) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        if (AuthenticatedHelper.isAdmin())
            return p2p_invoice_repository.findAll(new PageRequest(page, size));
        else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code = P2P_Helper.getCodeByUsername(username);
            return this.p2p_invoice_repository.findAllByRoot_SERCPTTIE(code, new PageRequest(page, size));
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isInvoiceRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                return this.p2p_invoice_repository.findAllByRoot_SERCPTTIE(code, new PageRequest(page, size));
            } else throw new RuntimeException("User not allowed to read resources exception...");
        }
        throw new RuntimeException("User not allowed exception...");
    }

}
