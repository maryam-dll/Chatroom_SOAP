package org.example.server.bean;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

    @XmlRootElement
    public class UserList {
        private List<String> users;

        public UserList() {
            this.users = new ArrayList<>();
        }

        public UserList(List<String> users) {
            this.users = users;
        }

        public List<String> getUsers() {
            return users;
        }

        public void setUsers(List<String> users) {
            this.users = users;
        }
    }


