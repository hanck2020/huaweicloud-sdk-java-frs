package com.huaweicloud.frs.client.result.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huaweicloud.frs.common.JSONObj;

/**
 * @author hanck@szkingdom.com
 * @date 2020年5月28日
 *
 */
public class SilentResult extends JSONObj{
	
	@JsonProperty(value = "alive")
	private boolean alive;
	
	@JsonProperty(value = "confidence")
	private double confidence;
	
	@JsonProperty(value = "picture")
	private String picture;
	
	public SilentResult(){
		
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Override
	public String toString() {
		return String.format("{\"alive\":%s,\"confidence\":%s,\"picture\":\"%s\"}",
                alive ? "true" : "false", String.valueOf(confidence), picture);
	}
}
