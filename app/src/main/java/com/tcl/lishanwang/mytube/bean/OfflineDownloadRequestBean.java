package com.tcl.lishanwang.mytube.bean;

/**
 * Created by xiaoshan on 2016/6/3.
 * 15:15
 */
public class OfflineDownloadRequestBean {

    /**
     * access_token : UUxgx9OiCRClFRxYVYMu-56xjTq3OadslVSEswyXo2xoOgrxuGvuWEcUd9Hb3uirjHWaWQcltTT2QSbAPHUXCZyjFZH4d1ItORiuyUc6kHFEX0GFMsP7wo2a9zweNEwF9yq-LmgjYBKqM1yvWBpq1L0erS_gSTP_ACbcERiJadI
     * push_token : vdZCXDC145wKggLXCrsIo7MZsLDZlwNV54UBci-X2VSxfbgAl0Xbwt8JNRCIG7jPkBxXBFlQ5Y7rWAWjc34wkSyDXzolGSF26sCaVSXRklf4pCFDEu2Xke4UAnRKUeeiP6yyHF1Rf0snx3f96ldf_XsrJiwK_ZO-8AXTv3obGZ2RNZZbAeU7Fv01g_K3-RFlujaE2jAKGL66TdIKXOGX21GVCgg-8kib-VOSC8R457o
     * videoid : NaFY0fJuIUM
     * format_tag : 18
     * preview : false
     * deviceid : xiaoshanchupinbishujingpin
     */

    public String access_token;
    public String push_token;
    public String videoid;
    public int format_tag;
    public boolean preview;
    public String deviceid;

    public OfflineDownloadRequestBean(String access_token, String push_token, String videoid, int format_tag, boolean preview, String deviceid) {
        this.access_token = access_token;
        this.push_token = push_token;
        this.videoid = videoid;
        this.format_tag = format_tag;
        this.preview = preview;
        this.deviceid = deviceid;
    }
}
