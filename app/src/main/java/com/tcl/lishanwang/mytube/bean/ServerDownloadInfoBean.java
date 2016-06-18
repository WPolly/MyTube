package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/6/3.
 * 14:54
 */
public class ServerDownloadInfoBean {

    /**
     * avespeed : 126018
     * bPreVideo : false
     * downloadstatus : {"download_url":"","extension":"mp4","itag":18,"progress":913,"resolution":"360p","status":1}
     * error : {"error_desc":"","error_id":0}
     * filename : NaFY0fJuIUM.mp4
     * filesize : 1.6555157E7
     * id : NaFY0fJuIUM
     * lefttime : 1.0999999962E10
     * preview : {"download_url":"","extension":"","itag":0,"progress":0,"resolution":"","status":0}
     */

    public int avespeed;
    public boolean bPreVideo;
    /**
     * download_url :
     * extension : mp4
     * itag : 18
     * progress : 913
     * resolution : 360p
     * status : 1
     */

    public DownloadstatusBean downloadstatus;
    /**
     * error_desc :
     * error_id : 0
     */

    public ErrorBean error;
    public String filename;
    public double filesize;
    public String id;
    public double lefttime;
    /**
     * download_url :
     * extension :
     * itag : 0
     * progress : 0
     * resolution :
     * status : 0
     */

    public PreviewBean preview;

    public static class DownloadstatusBean {
        public String download_url;
        public String extension;
        public int itag;
        public int progress;
        public String resolution;
        public int status;
    }

    public static class ErrorBean {
        public String error_desc;
        public int error_id;
    }

    public static class PreviewBean {
        public String download_url;
        public String extension;
        public int itag;
        public int progress;
        public String resolution;
        public int status;
    }
}
