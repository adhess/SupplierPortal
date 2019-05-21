package com.adhess.org.p2p.service.p2p_service.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document @Data @NoArgsConstructor
public class P2p_supplier {
    @Id
    private String _id;
    private Root root;


    @Data @NoArgsConstructor
    public class Root {
        private Long SERCPTTIE;
        private Long SERSUP;
        private String SUPCOD;
        private String CREDAT;
        private String UPDDAT;
        private String SUPADR;
        private String SUPNOM;
        private String SUPTYP;
    }
}

