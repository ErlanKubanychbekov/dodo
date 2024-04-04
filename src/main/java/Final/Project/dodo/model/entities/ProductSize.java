package Final.Project.dodo.model.entities;

import Final.Project.dodo.base.BaseEntity;
import Final.Project.dodo.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tb_product_size")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSize extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "price")
    BigDecimal price;
    @Column(name = "active")
    Boolean active;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;
    @Enumerated(EnumType.STRING)
    Status status;

    @Override
    protected void onCreate() {
        super.onCreate();
        status=Status.ACTIVE;
    }
}
