package satoshiyamamoto.tastewaf.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Todo {

    private @Id @GeneratedValue Long id;

    private @NotBlank String title;

    private boolean completed;

    private @CreatedDate Date createdAt;

    private @LastModifiedDate Date updatedAt;

}
