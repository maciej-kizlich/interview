package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User implements Model {

	private static final long serialVersionUID = -7787344007770946108L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Date userCreationDate;

    private Date lastLoginDate;

    @Transient
    private String repeatedPassword;

    private boolean enabled;

    private boolean reservationBlocked;
    
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    Set<Authority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    Set<Question> questions = new HashSet<>();

    public void addAuthority(Authority authoritiy) {
        this.authorities.add(authoritiy);
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(Date userCreationDate) {
        this.userCreationDate = userCreationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void enable() {
        this.setEnabled(true);
    }
	
    public boolean isEnabled() {
		return enabled;
	}
    
    public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    public boolean isReservationBlocked() {
		return reservationBlocked;
	}

	public void setReservationBlocked(boolean reservationBlocked) {
		this.reservationBlocked = reservationBlocked;
	}

	public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(username)
                .append(password)
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

        User user = (User) o;
        return new EqualsBuilder()
                .append(user.id, id)
                .append(user.username, username)
                .append(user.password, password)
                .isEquals();
    }
}
