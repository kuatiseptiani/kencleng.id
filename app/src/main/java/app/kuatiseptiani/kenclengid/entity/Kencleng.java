package app.kuatiseptiani.kenclengid.entity;

import java.io.Serializable;

/**
 * Created by kuatiseptiani on 07/01/19.
 */

public class Kencleng implements Serializable {

    private int id;
    private int nominal;
    private String catatan;

    public int getId() {
        return id;
    }

    public int getNominal() {
        return nominal;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
