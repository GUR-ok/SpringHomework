package homeworkSpringApp.model;

import homeworkSpringApp.model.MadBrains.MyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "numberLists")
public class NumberList extends MyList<Double> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numberList_id")
    private long id;

    @Column(name = "description")
    private String shortDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", nullable = false)
    private User user;

}
