package com.adhess.org.p2p.service.p2p_service.service.API;

import com.adhess.org.p2p.service.p2p_service.dao.P2p_Map_User_Supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_invoice_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_order_Repository;
import com.adhess.org.p2p.service.p2p_service.dao.P2p_supplier_Repository;
import com.adhess.org.p2p.service.p2p_service.service.AuthenticatedHelper;
import com.adhess.org.p2p.service.p2p_service.service.P2P_Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Portal_API {

    @Autowired
    private P2p_invoice_Repository p2p_invoice_repository;

    @Autowired
    private P2p_order_Repository p2p_order_repository;

    @GetMapping("/auth/getInfoStatusCharts")
    private ObjectNode getInfoStatusCharts() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ObjectNode invoiceNode = mapper.createObjectNode();

        if (AuthenticatedHelper.isAdmin()) {
            invoiceNode.put("ONGOING", p2p_invoice_repository.countAllByRoot_STATUS("ONGOING"));
            invoiceNode.put("ACCOUNTED", p2p_invoice_repository.countAllByRoot_STATUS("ACCOUNTED"));
            invoiceNode.put("REFUSED", p2p_invoice_repository.countAllByRoot_STATUS("REFUSED"));
            invoiceNode.put("BLOCKED", p2p_invoice_repository.countAllByRoot_STATUS("BLOCKED"));
        } else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code1 = P2P_Helper.getCodeByUsername(username);
            invoiceNode.put("ONGOING", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("ONGOING", code1));
            invoiceNode.put("ACCOUNTED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("ACCOUNTED", code1));
            invoiceNode.put("REFUSED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("REFUSED", code1));
            invoiceNode.put("BLOCKED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("BLOCKED", code1));
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isInvoiceRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                invoiceNode.put("ONGOING", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("ONGOING", code));
                invoiceNode.put("ACCOUNTED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("ACCOUNTED", code));
                invoiceNode.put("REFUSED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("REFUSED", code));
                invoiceNode.put("BLOCKED", p2p_invoice_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("BLOCKED", code));
            }
        }
        rootNode.set("invoice", invoiceNode);


        ObjectNode orderNode = mapper.createObjectNode();

        if (AuthenticatedHelper.isAdmin()) {
            orderNode.put("TRANSMITTED", p2p_order_repository.countAllByRoot_STATUS("0"));
            orderNode.put("CLOSED", p2p_order_repository.countAllByRoot_STATUS("1"));
        } else if (AuthenticatedHelper.isSupplier()) {
            String username = AuthenticatedHelper.findUsername();
            Long code1 = P2P_Helper.getCodeByUsername(username);
            orderNode.put("TRANSMITTED", p2p_order_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("0", code1));
            orderNode.put("CLOSED", p2p_order_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("1", code1));
        } else if (AuthenticatedHelper.isContact()) {
            if (AuthenticatedHelper.isInvoiceRead()) {
                String username = AuthenticatedHelper.getSupplierUsername();
                Long code = P2P_Helper.getCodeByUsername(username);
                orderNode.put("TRANSMITTED", p2p_order_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("0", code));
                orderNode.put("CLOSED", p2p_order_repository.countAllByRoot_STATUSAndRoot_SERCPTTIE("1", code));
            } else throw new RuntimeException("User not allowed to read resources exception...");
        }
        rootNode.set("order", orderNode);
        return rootNode;
    }
}
