package homeworkSpringApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



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
