package com.adhess.org.p2p.service.p2p_service.entities.intermediary;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class EPFDET {
    private BigInteger LIGNUM;
    private String MVTLIB;
    private BigInteger FACQTE;
    private BigInteger UNIPRX;
    private BigInteger DEVBRULIQ;
    private BigInteger DEVTVALIQ;
    private BigInteger UNIPRX1;
    private BigInteger SEREPFTET;
}
