package com.adhess.org.p2p.service.p2p_service.entities.intermediary;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class EPCTET {
    private String DEVCOD;
    private String CREDAT;
    private String DEALIVDAT;
    private Double DEVBRUMNT;
    private Double DEVTVAMNT;
    private String STATUS;
    private BigInteger EPCNUM;
    private String EXPLIVDAT;
    private BigInteger SEREPCTET;
    private BigInteger GLOBAL_REVERSAL_ID;
    private BigInteger SERCPTTIE;
    private String EPMADRLIB;
    private String[] PARTIAL_REVERSAL_DATE;
    private EPCDET[] EPCDET;
}
