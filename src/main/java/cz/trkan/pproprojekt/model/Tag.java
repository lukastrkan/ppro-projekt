package cz.trkan.pproprojekt.model;

import jakarta.persistence.*;import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "tags")
public class Tag{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    @Column(unique = true)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
