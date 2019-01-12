package RPIS_61.Arisova.wdad.learn.xml;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    public Officiant officiant;
    public List<Item> items;

    public Order(Officiant officiant, List<Item> items){
        this.officiant = officiant;
        this.items = items;
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public void setOfficiant(Officiant officiant) {
        this.officiant = officiant;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
