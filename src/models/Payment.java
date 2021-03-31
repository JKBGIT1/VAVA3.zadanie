package models;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    private Accommodation accommodation;
    private Date date;
    private boolean inCash;
}
