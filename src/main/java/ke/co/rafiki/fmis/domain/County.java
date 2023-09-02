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
@Table(name = "counties")
public class County {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Constituency> constituencies;

    @ToString.Exclude
    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCounty> subCounties;

    @ToString.Exclude
    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL)
    private List<Farm> farms;
}
