package ke.co.rafiki.fmis.domain;

import java.time.LocalDate;

public class SalesRecord extends BaseEntityAudit {
    private LocalDate date;
    private SalesType type;
    private Double amount;
    private String description;
}
