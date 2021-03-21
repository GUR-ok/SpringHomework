package homeworkSpringApp.model;

import homeworkSpringApp.model.MadBrains.MyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "numberLists")
public class NumberList{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numberList_id")
    private long id;

    @Column(name = "description")
    private String shortDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", nullable = false)
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "elements", joinColumns = @JoinColumn(name = "user_list"))
    private List<Double> numlist = new ArrayList<>();

}
