package com.activeviam.creator.model;

public class Cluster {
	
	private String aksName;
	private String vmCount;
	private String vmSize;
	private String tag;
	private String subscriptionId;
	private String dbServerName;
	private String dbAdminUsername;
	private String dbAdminPassword;
	private String dbName;
 	private String keycloakUser;
	private String keycloakPassword;
	private String dockerUserName;
	private String dockerPassword;
	private String dockerEmail;
	private String domainName;
	private String standardAksName;
	private String githubRepository;
	private String githubBranch;
	private String location;
	//
	
	public String getAksName() {
		return aksName;
	}

	public void setAksName(String aksName) {
		this.aksName = aksName;
	}
	
	
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
	
	public String getDbServerName() {
		return dbServerName;
	}
	public void setDbServerName(String dbServerName) {
		this.dbServerName = dbServerName;
	}
	public String getDbAdminUsername() {
		return dbAdminUsername;
	}
	public void setDbAdminUsername(String dbAdminUsername) {
		this.dbAdminUsername = dbAdminUsername;
	}
	public String getDbAdminPassword() {
		return dbAdminPassword;
	}
	public void setDbAdminPassword(String dbAdminPassword) {
		this.dbAdminPassword = dbAdminPassword;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getKeycloakUser() {
		return keycloakUser;
	}
	public void setKeycloakUser(String keycloakUser) {
		this.keycloakUser = keycloakUser;
	}
	public String getKeycloakPassword() {
		return keycloakPassword;
	}
	public void setKeycloakPassword(String keycloakPassword) {
		this.keycloakPassword = keycloakPassword;
	}
	public String getDockerUserName() {
		return dockerUserName;
	}
	public void setDockerUserName(String dockerUserName) {
		this.dockerUserName = dockerUserName;
	}
	public String getDockerPassword() {
		return dockerPassword;
	}
	public void setDockerPassword(String dockerPassword) {
		this.dockerPassword = dockerPassword;
	}
	public String getDockerEmail() {
		return dockerEmail;
	}
	public void setDockerEmail(String dockerEmail) {
		this.dockerEmail = dockerEmail;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getStandardAksName() {
		return standardAksName;
	}

	public void setStandardAksName(String standardAksName) {
		this.standardAksName = standardAksName;
	}
	
	public String getGithubRepository() {
		return githubRepository;
	}
	public void setGithubRepository(String githubrepository) {
		this.githubRepository = githubrepository;
	}
	public String getGithubBranch() {
		return githubBranch;
	}
	public void setGithubBranch(String githubBranch) {
		this.githubBranch = githubBranch;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
