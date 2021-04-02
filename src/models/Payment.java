package models;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    private Date date;
    private boolean inCash;

    public Payment(Date date, boolean inCash) {
        this.date = date;
        this.inCash = inCash;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isInCash() {
        return inCash;
    }

    public void setInCash(boolean inCash) {
        this.inCash = inCash;
    }
}
