package ke.co.rafiki.fmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "constituencies")
public class Constituency {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;

    @ToString.Exclude
    @OneToMany(mappedBy = "constituency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCounty> subCounties;

    @ToString.Exclude
    @OneToMany(mappedBy = "constituency", cascade = CascadeType.ALL)
    private List<Farm> farms;
}
