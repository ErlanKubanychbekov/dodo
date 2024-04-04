package Final.Project.dodo.base;

import Final.Project.dodo.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Date;
@MappedSuperclass
@Data
public abstract class BaseDto {
    protected Long id;
    protected LocalDateTime addDate;
    protected LocalDateTime updateDate;
    @Enumerated(EnumType.STRING)
    protected Status status;
}
