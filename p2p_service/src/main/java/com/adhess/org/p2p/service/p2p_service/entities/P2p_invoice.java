package com.adhess.org.p2p.service.p2p_service.entities;


import com.adhess.org.p2p.service.p2p_service.entities.intermediary.EPFTET;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class P2p_invoice {
    @Id
    private String _id;
    private EPFTET root;
}
