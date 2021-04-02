package models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsedService implements Serializable {
    private Service service;
    private Date date;

    public UsedService(Service service, Date date) {
        this.service = service;
        this.date = date;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateAsString() {
        // Taken from https://www.javatpoint.com/java-simpledateformat
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(this.date);
    }
}
