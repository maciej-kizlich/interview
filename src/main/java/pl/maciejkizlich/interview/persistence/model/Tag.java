package pl.maciejkizlich.interview.persistence.model;

import javax.persistence.*;

/**
 * Created by Denis_Ivanchenko on 7/21/2014.
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Tag.findByTitle", query = "select t from Tag t where t.title = :title"),
    @NamedQuery(name = "Tag.findTitleLike", query = "select t from Tag t where t.title like :regexp")
})
public class Tag implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
