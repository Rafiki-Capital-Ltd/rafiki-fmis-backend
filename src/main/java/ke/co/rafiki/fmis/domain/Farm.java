package ke.co.rafiki.fmis.domain;

import java.util.List;

public class Farm extends BaseEntityAudit {
    private User owner;
    private Double size;
    private String location;
    private String county;
    private String ward;
    private String nearestShoppingCenter;
    private List<ValueChainAddition> valueChainAdditions;
    private FarmDiary farmDiary;
}
