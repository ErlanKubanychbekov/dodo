package Final.Project.dodo.model.entities;

import Final.Project.dodo.base.BaseEntity;
import Final.Project.dodo.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tb_product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "logo")
    String logo;
    @Column(name = "description")
    String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Categories categories;
    @Enumerated(EnumType.STRING)
    Status status;

    @Override
    protected void onCreate() {
        super.onCreate();
        status= Status.ACTIVE;
    }
}
