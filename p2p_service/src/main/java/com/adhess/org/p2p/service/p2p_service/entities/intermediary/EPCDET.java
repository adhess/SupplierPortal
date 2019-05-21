package com.adhess.org.p2p.service.p2p_service.entities.intermediary;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class EPCDET {
        private BigInteger SERCOA;
        private String ARTLIB;
        private BigInteger CMDQTE;
        private BigInteger DEVBRUMNT;
        private BigInteger DEVTVAMNT;
        private BigInteger LIGNUM;
        private String CONLIB1;
        private String CONLIB2;
        private String CONLIB3;
        private String CONLIB4;
        private BigInteger SEREPCTET;
}
