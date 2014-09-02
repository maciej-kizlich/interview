package pl.maciejkizlich.interview.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Authority implements Model {

    @Id
    private long id;

    private String authority;


    public String getAuthority() {
		return authority;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Authority auth = (Authority) o;
        return new EqualsBuilder()
                .append(auth.id, id)
                .append(auth.authority, authority)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(authority)
                .toHashCode();
    }
}
