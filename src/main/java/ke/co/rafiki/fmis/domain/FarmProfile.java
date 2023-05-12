package ke.co.rafiki.fmis.domain;

import java.util.List;
import java.util.UUID;

public class FarmProfile {
    private UUID id;
    private User owner;
    private Double size;
    private String location;
    private String county;
    private String ward;
    private String nearestShoppingCenter;
    private List<ValueChainAddition> valueChainAdditions;
}
