package com.example.prafulmishra.smartboard_alpha;

public class ListData {
    String notice_id,notice,time;

    public ListData(String notice_id,String notice, String time) {

        this.notice_id = notice_id;
        this.notice = notice;
        this.time = time;

    }

    public String getNotice_id() {
        return notice_id;
    }

    public String getNotice() {
        return notice;
    }

    public String getTime() {
        return time;
    }


}
