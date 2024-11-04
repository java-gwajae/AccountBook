package org.gwajae.accountbook.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar {
    private String calendar_id;
    private String user_id;
    private String type;
    private String category;
    private int amount;
    private Date date;
    private String description;

    public Calendar(String calendar_id, String user_id, String type, String category, int amount, Date date, String description) {
        this.calendar_id = calendar_id;
        this.user_id = user_id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public String getCalendarId() {
        return calendar_id;
    }

    public void setCalendarId(String cid) {
        this.calendar_id = cid;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String uid) {
        this.user_id = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
