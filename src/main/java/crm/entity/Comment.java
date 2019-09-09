package crm.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(name = "comment_desc")
    private String commentDesc;

    @ManyToOne
    private Product product;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created")
    private Date created;

    @Column(name = "last_update")
    private Date lastUpdate;

    public Comment() {
    }

    public Comment(@NotNull(message = "is required") @Size(min = 1, message = "is required") String commentDesc) {
        this.commentDesc = commentDesc;
    }

    public Comment(@NotNull(message = "is required") @Size(min = 1, message = "is required") String commentDesc, Product product, Long userId, Date created, Date lastUpdate) {
        this.commentDesc = commentDesc;
        this.product = product;
        this.userId = userId;
        this.created = created;
        this.lastUpdate = lastUpdate;
    }

    public Comment(int id, @NotNull(message = "is required") @Size(min = 1, message = "is required") String commentDesc,
                   Product product, Long userId, Date created, Date lastUpdate) {
        this.id = id;
        this.commentDesc = commentDesc;
        this.product = product;
        this.userId = userId;
        this.created = created;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.getComments().remove(this);
        }
        this.product = product;
        this.product.getComments().add(this);
    }

    @Override
    public String toString() {
        return "Comment [id=" + id + ", commentDesc=" + commentDesc + ", userId=" + userId
                + ", created=" + created + ", lastUpdate=" + lastUpdate
                + ", ProductId=" + ((getProduct() == null) ? null : getProduct().getId() + "]");
    }
}
