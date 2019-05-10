package com.activeviam.creator.model;

public class Cluster {
	
	private String aksName;
	private String vmCount;
	private String vmSize;
	private String tag;
	private String subscriptionId;
	
	//
	
	public String getAksName() {
		return aksName;
	}
	public void setAksName(String aksName) {
		this.aksName = aksName;
	}
	
	//
		
	//
	
	public String getVmCount() {
		return vmCount;
	}
	public void setVmCount(String vmCount) {
		this.vmCount = vmCount;
	}
	
	//
	
	public String getVmSize() {
		return vmSize;
	}
	public void setVmSize(String vmSize) {
		this.vmSize = vmSize;
	}
	
	//
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	
	

}
