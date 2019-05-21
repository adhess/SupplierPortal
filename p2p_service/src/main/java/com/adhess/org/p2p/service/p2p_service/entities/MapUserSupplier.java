package com.adhess.org.p2p.service.p2p_service.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class MapUserSupplier {
    public String _id;
    public String username;
    public String id_supplier;

    public MapUserSupplier(String username, String id_supplier) {
        this.username = username;
        this.id_supplier = id_supplier;
    }
}
