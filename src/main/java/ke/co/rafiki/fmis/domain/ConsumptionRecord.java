package ke.co.rafiki.fmis.domain;

import java.time.LocalDate;

public class ConsumptionRecord extends BaseEntityAudit {
    private Farm farm;
    private LocalDate date;
    private Double quantity;
}
