package ke.co.rafiki.fmis.domain;

import java.time.LocalDate;

public class ProductionRecord extends BaseEntityAudit {
    private Farm farm;
    private LocalDate date;
    private Double quantity;
}
