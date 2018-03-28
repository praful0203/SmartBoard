
package com.example.prafulmishra.smartboard_alpha;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataGson {

    @SerializedName("notice_id")
    @Expose
    private String noticeId;
    @SerializedName("notice")
    @Expose
    private String notice;
    @SerializedName("time")
    @Expose
    private String time;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
