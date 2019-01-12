package RPIS_61.Arisova.wdad.learn.xml;

import java.io.Serializable;

public class Officiant implements Serializable {
    private String firstName, secondName;

    public Officiant(String firstName, String secondName){
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
