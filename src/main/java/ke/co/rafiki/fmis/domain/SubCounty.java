package ke.co.rafiki.fmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_counties")
public class SubCounty {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;

    @ToString.Exclude
    @OneToMany(mappedBy = "subCounty", cascade = CascadeType.ALL)
    private List<Farm> farms;
}
