package pl.maciejkizlich.interview.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name="library_settings")
public class LibraryConfig implements Model{
	
	@Id
	private String key;
	
	private String value;
	
	@Column(updatable=false)
	private String description;
	
	public LibraryConfig() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .append(value)
                .append(description)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibraryConfig config = (LibraryConfig) o;
        return new EqualsBuilder()
                .append(config.key, key)
                .append(config.value, value)
                .append(config.description, description)
                .isEquals();
    }
}
