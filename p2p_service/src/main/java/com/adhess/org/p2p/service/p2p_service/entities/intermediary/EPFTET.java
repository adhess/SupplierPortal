package com.adhess.org.p2p.service.p2p_service.entities.intermediary;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;


@Data
@NoArgsConstructor
public class EPFTET {
    private String DOCREF;
    private String RECDAT;
    private String ECHDAT;
    private BigInteger DEVTVAMNT;
    private BigInteger DEVNETMNT;
    private BigInteger devBruMnt;
    private String DEVCOD;
    private BigInteger ESCPRC;
    private String communication;
    private String NDCFLG;
    private BigInteger SERECATET;
    private BigInteger SEREPFTET;
    private BigInteger SERCPTTIE;
    private String STATUS;
    private EPFDET[] EPFDET;
}
