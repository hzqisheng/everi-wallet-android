package com.smallcat.shenhai.mvpbase.model.bean;

/**
 * 作者： MirsFang on 2018/12/16 20:47
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class CollectBean {


    /**
     * dataUrl : data:image/png;base64,iV2522
     * rawText : https://evt.li/03$5CLY539F21:FCRD2-B6URN*98O4SMC:N82YNDXI5LQ$03OFA6ED8HX0AK3OBCTUOLRE7P1ZQDS:5R*MD+
     * timeConsumed : 4
     */

    private String dataUrl;
    private String rawText;
    private int timeConsumed;

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public int getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(int timeConsumed) {
        this.timeConsumed = timeConsumed;
    }
}
