package pl.maciejkizlich.interview.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsersList {

    private List<User> users;

    @XmlElement
    public List<User> getUsersList() {
        if (users == null) {
            users = new ArrayList<User>();
        }
        return users;
    }
}
