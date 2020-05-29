package com.huaweicloud.frs.client.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huaweicloud.frs.client.result.common.SilentResult;
import com.huaweicloud.frs.client.result.common.Warning;
import com.huaweicloud.frs.common.JSONObj;

import java.util.List;

/**
 * Result of silent live detect
 */
public class LiveDetectSilentResult extends JSONObj {

    @JsonProperty(value = "result")
    private SilentResult result;

    @JsonProperty(value = "warning-list")
    private List<Warning> warningList;

    public SilentResult getSilentResult() {
        return result;
    }

    public void setSilentResult(SilentResult result) {
        this.result = result;
    }

    public List<Warning> getWarningList() {
        return warningList;
    }

    public void setWarningList(List<Warning> warningList) {
        this.warningList = warningList;
    }

    public String toString() {
        return String.format("{\"result\":%s,\"warningList\":%s}",
        		result.toString(), warningList.toString());
    }

}
