package com.example.prafulmishra.smartboard_alpha;

/**
 * Created by PrafulMishra on 19-03-2018.
 */

public class GetNotice {
        String idnotice;
        String notice;
        String time;

        public GetNotice()
    {

    }

    public GetNotice(String idnotice, String notice, String time) {
        this.idnotice = idnotice;
        this.notice = notice;
        this.time = time;
    }

    public String getIdnotice() {
        return idnotice;
    }

    public String getNotice() {
        return notice;
    }

    public String getTime() {
        return time;
    }

    public void setIdnotice(String idnotice) {
        this.idnotice = idnotice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


