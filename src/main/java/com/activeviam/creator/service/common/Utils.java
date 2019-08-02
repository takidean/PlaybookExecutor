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
		String installInit = "helm init";

		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c",installInit);
		Process process = builder.start();

		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);	
	}
	
	//install Helm
	public static void helmInstall( int id, String logsPath) throws IOException {
		String installNginx ="helm install --name ngin-ingress stable/nginx-ingress";
		Path path = Paths.get(logsPath + "/" + id + ".txt");

 		System.out.println(installNginx);
		ProcessBuilder builderFileNginx = new ProcessBuilder();
		builderFileNginx.command("bash", "-c",installNginx);
		Process processFileNginx = builderFileNginx.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		System.out.println("****");
		BufferedReader readerFileNginx = new BufferedReader(new InputStreamReader(processFileNginx.getInputStream()));
		String line = "";
		while ((line = readerFileNginx.readLine()) != null) {
			System.out.println(line );
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
	}
	
	//Helm apply nginx controller
	
	public static void applyNginxController(String fileNginxPath, int id, String logsPath) throws IOException {
	System.out.println(" *** * * *apply nginx Controller ");
	Path path = Paths.get(logsPath + "/" + id + ".txt");

	String kubeApplyIngress = "kubectl apply -f " + fileNginxPath;
	ProcessBuilder builderFileNginx = new ProcessBuilder();
	builderFileNginx.command("bash", "-c",kubeApplyIngress);
	Process processFileNginx = builderFileNginx.start();
	Charset charset = StandardCharsets.UTF_8;
	StringBuilder output = new StringBuilder();

	BufferedReader readerFileNginx = new BufferedReader(new InputStreamReader(processFileNginx.getInputStream()));
	String line = "";
	while ((line = readerFileNginx.readLine()) != null) {
		System.out.println(line );
		output.append(line + "\n");
	}
	Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
	}

	
	public static void deployKeycloak(String fileKeycloakDeploymentPath, String fileKeycloakPvcPath, int id, String logsPath)
			throws IOException {
		Path path = Paths.get(logsPath + "/" + id + ".txt");
		String installNginx = "kubectl apply -f "+fileKeycloakPvcPath;
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c",installNginx);
		Process process = builder.start();
		System.out.println("deployKeycloak ------------------ kubectl apply -f keycloak ");
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
		if (!output.toString().contains("Error")) {}
	}

	// deploy keycloak pod
	public static void deployKeycloakPod(String fileKeycloakDeploymentPath, String fileKeycloakPvcPath, int id, String logsPath) throws IOException {
	String kubeApplyIngress = "kubectl apply -f " + fileKeycloakDeploymentPath;
	ProcessBuilder builderFileKeycloakDep = new ProcessBuilder();
	builderFileKeycloakDep.command("bash", "-c",kubeApplyIngress);
	Process processFileKeycloakDep = builderFileKeycloakDep.start();
	Charset charset = StandardCharsets.UTF_8;
	Path path = Paths.get(logsPath + "/" + id + ".txt");
	System.out.println("Deploy keycloak pod");
	String line = "";
	StringBuilder output = new StringBuilder();
	BufferedReader readerFileKeycloakDep = new BufferedReader(new InputStreamReader(processFileKeycloakDep.getInputStream()));
		while ((line = readerFileKeycloakDep.readLine()) != null) {
			System.out.println(line);
		output.append(line + "\n");
	}
	Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
	}
	
	// change firewall settings 
	public static void changeFirewallIpAddress(Cluster cluster,int taskid, String logsPath) throws IOException {
		String firewallConfig="az sql server firewall-rule create -g "+cluster.getAksName()+"_"+cluster.getTag() +"  -s "+cluster.getDbServerName()+"  -n myrule --start-ip-address 1.2.3.4 --end-ip-address 5.6.7.8 --subscription "+cluster.getSubscriptionId();
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c",firewallConfig);
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
		
	String kubectlSecret="kubectl create secret docker-registry activeviam.jfrog.io --docker-server=https://activeviam.jfrog.io/activeviam --docker-username="+cluster.getDockerUserName()+" --docker-password="+cluster.getDockerPassword()+" --docker-email="+cluster.getDockerEmail();
	ProcessBuilder builder = new ProcessBuilder();
	builder.command("bash", "-c",kubectlSecret);
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
	
	//Secret Creation
	public static void applyKubeFiles(String generatedCreationKeycloakSecretFilePath ,int taskid, String logsPath) throws IOException {
		String createKeycloakSecret="kubectl apply -f "+generatedCreationKeycloakSecretFilePath;
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c",createKeycloakSecret);
		Process process= builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));	
		String line="";
		while ((line = reader.readLine()) != null) {
			System.out.println("**************** " + line);
			output.append(line + "\n");
		}
		Path path = Paths.get(logsPath + "/" + taskid + ".txt");
		Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);	

	}
	

	
	public static void createDomaineNameTlsCert(String certFilePath,String keyFilePath,int taskid, String logsPath) throws IOException {
		String createCertificate=	"kubectl create secret tls active-tls-cert --key "+keyFilePath+"/tls.key --cert "+certFilePath+"tls.crt";
		System.out.println("create tls secret"+keyFilePath +" cert " +certFilePath);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c",createCertificate);
		Process process= builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));	
		String line="";
		while ((line = reader.readLine()) != null) {
			System.out.println("**************** " + line);
			output.append(line + "\n");
		}
		Path path = Paths.get(logsPath + "/" + taskid + ".txt");
		Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);	
	}
	
	
}
