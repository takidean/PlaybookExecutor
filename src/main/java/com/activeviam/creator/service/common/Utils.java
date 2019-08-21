package com.activeviam.creator.service.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;

import com.activeviam.creator.model.Cluster;
import com.offbytwo.jenkins.JenkinsServer;

public class Utils {

	public static void createNginx(String fileNginxPath, int id, String logsPath) throws IOException {
		String installInit = "helm init";
		runCommand(installInit, id, logsPath);
	}

	// install Helm
	public static void helmInstall(int id, String logsPath) throws IOException, InterruptedException {
		String installNginx = "helm install --name ngin-ingress stable/nginx-ingress";

		StringBuilder output = runCommand(installNginx, id, logsPath);
		if (!output.toString().contains("DEPLOYED")) {
			Thread.sleep(5000, 0);
			helmInstall(id, logsPath);
		}
	}

	// Helm apply nginx controller

	public static void applyNginxController(String fileNginxPath, int id, String logsPath)
			throws IOException, InterruptedException {
		String kubeApplyIngress = "kubectl apply -f " + fileNginxPath;
		runCommand(kubeApplyIngress, id, logsPath);
	}

	public static void deployKeycloak(String fileKeycloakDeploymentPath, String fileKeycloakPvcPath, int id,
			String logsPath) throws IOException, InterruptedException {
		String installNginx = "kubectl apply -f " + fileKeycloakPvcPath;
		runCommand(installNginx, id, logsPath);
	}

	// deploy keycloak pod
	public static void deployKeycloakPod(String fileKeycloakDeploymentPath, String fileKeycloakPvcPath, int id,
		String logsPath) throws IOException, InterruptedException {
		String kubectlgetPvc = "kubectl get pvc";
		ProcessBuilder builderVerifyKubectlPvc = new ProcessBuilder();
		builderVerifyKubectlPvc.command("bash", "-c", kubectlgetPvc);
		Process processVerifyPvc = builderVerifyKubectlPvc.start();
		String line = "";
		StringBuilder output = new StringBuilder();
		BufferedReader readerFileVerifyPvc = new BufferedReader(
				new InputStreamReader(processVerifyPvc.getInputStream()));
		while ((line = readerFileVerifyPvc.readLine()) != null) {
			output.append(line + "\n");
		}
		if (output.toString().contains("Bound")) {
			Thread.sleep(30000, 0);
			String kubeApplyIngress = "kubectl apply -f " + fileKeycloakDeploymentPath;
			runCommand(kubeApplyIngress, id, logsPath);
		} else {
			deployKeycloakPod(fileKeycloakDeploymentPath, fileKeycloakPvcPath, id, logsPath);
		}
	}

	// change firewall settings
	public static void changeFirewallIpAddress(Cluster cluster, int taskid, String logsPath) throws IOException {
		String firewallConfig = "az sql server firewall-rule create -g " + cluster.getAksName() + "  -s "
				+ cluster.getDbServerName().toLowerCase()
				+ "  -n myrule --start-ip-address 1.1.1.1 --end-ip-address 255.255.255.254 --subscription "
				+ cluster.getSubscriptionId();
		runCommand(firewallConfig, taskid, logsPath);
	}

	public static void createSecretDocker(Cluster cluster, int taskid, String logsPath)
			throws IOException, InterruptedException {

		String kubectlSecret = "kubectl create secret docker-registry activeviam.jfrog.io --docker-server=https://activeviam-delivery-docker-internal.jfrog.io/v2/ --docker-username="
				+ cluster.getDockerUserName() + " --docker-password=" + cluster.getDockerPassword() + " --docker-email="
				+ cluster.getDockerEmail();
		runCommand(kubectlSecret, taskid, logsPath);
	}

	// Secret Creation
	public static void applyKubeFiles(String generatedCreationKeycloakSecretFilePath, int taskid, String logsPath)
			throws IOException {
		String createKeycloakSecret = "kubectl apply -f " + generatedCreationKeycloakSecretFilePath;
		runCommand(createKeycloakSecret, taskid, logsPath);
	}

	public static void createDomaineNameTlsCert(String certFilePath, String keyFilePath, int taskid, String logsPath)
			throws IOException {
		String createCertificate = "kubectl create secret tls active-tls-cert --key " + keyFilePath + " --cert "
				+ certFilePath;
		runCommand(createCertificate, taskid, logsPath);
	}

	public static void connectJenkins(String configFilePath,String jenkinsUser, String jenkinsToken,String aksName, String jenkinsUrl) throws URISyntaxException, IOException {		
		JenkinsServer jenkins = new JenkinsServer(new URI(jenkinsUrl), jenkinsUser, jenkinsToken);
		Charset charset = StandardCharsets.UTF_8;
 		String content = new String(Files.readAllBytes(Paths.get(configFilePath)), charset);
  		System.out.println(content);
  		System.out.println("**** " +(jenkins.getJobXml("PipelineDeploy").toString()));
 	//	jenkins.createJob(aksName+"pipeline", content);
// 		jenkins.createJob("pipeline", jenkins.getJobXml("PipelineDeploy").toString());

	}
	
	// Create cluster
	public static Cluster createCompleteClusterInformations(Cluster cluster, String dbAdminUsername,
			String keycloakUser, String dockerUserName, String dockerEmail) {
		Cluster clusterComplete = new Cluster();
		clusterComplete.setAksName(cluster.getAksName() + cluster.getTag());
		clusterComplete.setSubscriptionId(cluster.getSubscriptionId());
		String standardAksName = "standard" + cluster.getAksName() + cluster.getTag();
		clusterComplete.setStandardAksName(standardAksName);
		clusterComplete.setDomainName(cluster.getDomainName());
		clusterComplete.setVmSize(cluster.getVmSize());
		clusterComplete.setDbAdminPassword(cluster.getDbAdminPassword());
		clusterComplete.setKeycloakPassword(cluster.getKeycloakPassword());
		clusterComplete.setDockerPassword(cluster.getDockerPassword());
		clusterComplete.setDbServerName("dbserver" + standardAksName);
		clusterComplete.setDbName("db" + standardAksName);
		clusterComplete.setTag(cluster.getTag());
		clusterComplete.setDbAdminUsername(dbAdminUsername);
		clusterComplete.setKeycloakUser(keycloakUser);
		clusterComplete.setDockerUserName(dockerUserName);
		clusterComplete.setDockerEmail(dockerEmail);

		return clusterComplete;
	}

	public static void fileGenerator(String filePath, String templatefile) throws IOException {
		File copied = new File(filePath);
		FileUtils.copyFile(new File(templatefile), copied);
	}

	public static StringBuilder runCommand(String command, int taskid, String logsPath) throws IOException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("bash", "-c", command);
		Process process = builder.start();
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Path path = Paths.get(logsPath + "/" + taskid + ".txt");
		Files.write(path, output.toString().getBytes(charset), StandardOpenOption.APPEND);
		return output;
	}
}
