package crm.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "name")
    private String name;

    @NotNull(message = "is required")
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comments=" + comments +
                '}';
    }
}
