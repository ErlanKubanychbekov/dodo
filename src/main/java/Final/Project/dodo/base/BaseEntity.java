package Final.Project.dodo.base;


import Final.Project.dodo.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    protected LocalDateTime addDate;
    protected LocalDateTime updateDate;
//    @Enumerated(EnumType.STRING)
//    protected Status status;

    @PrePersist
    protected void onCreate(){
        addDate= LocalDateTime.now();
        updateDate= LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }
}
