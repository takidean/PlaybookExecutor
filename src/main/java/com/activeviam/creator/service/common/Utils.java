package com.activeviam.creator.service.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.activeviam.creator.model.Cluster;

public class Utils {

	public static void createNginx(String fileNginxPath, int id, String logsPath) throws IOException {
		Path path = Paths.get(logsPath + "/" + id + ".txt");
		String installNginx = "helm install --name nginx-ingress stable/nginx-ingress";
		ProcessBuilder builder = new ProcessBuilder(installNginx);
		Process process = builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
		if (!output.toString().contains("Error")) {
			String kubeApplyIngress = "kubectl apply -f" + fileNginxPath;
			builder = new ProcessBuilder(kubeApplyIngress);
			process = builder.start();
		}
	}
		
	public static void deployKeycloak(String fileKeycloakDeploymentPath, String fileKeycloakPvcPath, int id, String logsPath)
			throws IOException {
		Path path = Paths.get(logsPath + "/" + id + ".txt");
		String installNginx = "kubectl apply -f"+fileKeycloakPvcPath;
		ProcessBuilder builder = new ProcessBuilder(installNginx);
		Process process = builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
		if (!output.toString().contains("Error")) {
			String kubeApplyIngress = "kubectl apply -f" + fileKeycloakDeploymentPath;
			builder = new ProcessBuilder(kubeApplyIngress);
			process = builder.start();
			 reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
 			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
		}
	}

	// change firewall settings 
	// 

	public static void changeFirewallIpAddress(Cluster cluster,int taskid, String logsPath) throws IOException {
		String firewallConfig="az sql server firewall-rule create -g "+cluster.getAksName()+"_"+cluster.getTag() +"  -s "+cluster.getDbServerName()+"  -n myrule --start-ip-address 1.2.3.4 --end-ip-address 5.6.7.8 --subscription "+cluster.getSubscriptionId();
		ProcessBuilder builder = new ProcessBuilder(firewallConfig);
		Process process= builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));	
		String line="";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Path path = Paths.get(logsPath + "/" + taskid + ".txt");
		Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);
	}
	
	//public static String getServiceIpAddress()
	
	public static void createSecretDocker(Cluster cluster,int taskid, String logsPath) throws IOException {
		
	String login=	"kubectl create secret  docker-registry activeviam.jfrog.io   --docker-server=https://activeviam.jfrog.io/activeviam --docker-username="+cluster.getDockerUserName()+" --docker-password="+cluster.getDockerPassword()+" --docker-email="+cluster.getDockerEmail();		
	ProcessBuilder builder = new ProcessBuilder(login);
	Process process= builder.start();
	Charset charset = StandardCharsets.UTF_8;
	StringBuilder output = new StringBuilder();
	BufferedReader reader = new BufferedReader(
			new InputStreamReader(process.getInputStream()));	
	String line="";
	while ((line = reader.readLine()) != null) {
		output.append(line + "\n");
	}
	Path path = Paths.get(logsPath + "/" + taskid + ".txt");
	Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);
	}
	
	public static void createDomaineNameTlsCert(String certFilePath,String keyFilePath,int taskid, String logsPath) throws IOException {
		String createCertificate=	"kubectl create secret tls active-tls-cert --key "+keyFilePath+" --cert "+certFilePath;
		ProcessBuilder builder = new ProcessBuilder(createCertificate);
		Process process= builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));	
		String line="";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Path path = Paths.get(logsPath + "/" + taskid + ".txt");
		Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);	
	}
	
}
