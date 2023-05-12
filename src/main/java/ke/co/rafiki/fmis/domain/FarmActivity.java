package ke.co.rafiki.fmis.domain;

import java.time.LocalDate;

public class FarmActivity extends BaseEntityAudit {
    private FarmDiary farmDiary;
    private LocalDate date;
    private String activities;
}
