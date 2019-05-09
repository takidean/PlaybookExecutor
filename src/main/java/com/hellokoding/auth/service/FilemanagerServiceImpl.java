package com.hellokoding.auth.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.Cluster;


@Service
public class FilemanagerServiceImpl {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private String SUBSCRIPTION_ID="Subscription_Id";

	@Value("${client_id}")
	String clientId;
	
	@Value("${client_secret}")
	String clientSecret;

	@Value("${generatedfile.path}")
	String generatedFilePath;
	
	@Value("${templatecluster.path}")
	String templateFilePath;
	
	@Value("${credentials.path}")
	String credentialsFilePath;
	

	@Value("${standardcluster.template.path}")
	String templateStandardFilePath;

	@Value("${standardcluster.generatedfile.path}")
	String generatedStandardFilePath;

	@Value("${removeresourcegroup.template.path}")
	String templateRemoveResourceGroupFilePath;

	@Value("${removeresourcegroup.generatedfile.path}")
	String generatedRemoveGroupFilePath;

	
	public String replaceFileContent(Path path, Cluster cluster) throws IOException {
				
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("resource_group_value", "MC_delivery_westeurope_"+cluster.getAksName()+"_"+cluster.getTag());
		content = content.replaceAll("aks_name_value", cluster.getAksName());
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/.ssh/id_rsa.pub")));  
		content = content.replaceAll("ssh-rsa_value", ssh_key);
		content = content.replaceAll("client_id_value", clientId);
		content = content.replaceAll("client_secret_value", clientSecret);
		content = content.replaceAll("count_value", cluster.getVmCount());
		content = content.replaceAll("vm_size_value", cluster.getVmSize());
		content = content.replaceAll("env_value", cluster.getTag());
		Files.write(path, content.getBytes(charset));
		return content;

	}
	
	public String replaceResourceGroupRemovalFileContent(Path path, String aks_name, String tag) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/.ssh/id_rsa.pub")));  
		content = content.replaceAll("ssh-rsa_value", ssh_key);
		content = content.replaceAll("client_id_value", clientId);
		content = content.replaceAll("client_secret_value", clientSecret);		
		content = content.replaceAll("resource_group_value", "MC_delivery_westeurope_"+aks_name+"_"+tag);
		Files.write(path, content.getBytes(charset));
		return content;
	}
	
	
	public String removeCreatedResourceGroup() throws IOException, InterruptedException {
	    return runPlayBook(generatedRemoveGroupFilePath);
	}
	
	public String standardAksCreator(Cluster cluster) throws IOException {
		
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedStandardFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("resource_group_value", "MC_delivery_westeurope_"+cluster.getAksName()+"_"+cluster.getTag());
		content = content.replaceAll("aks_name_value", "Standard_"+cluster.getAksName());
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/.ssh/id_rsa.pub")));  
		content = content.replaceAll("ssh-rsa_value", ssh_key);
		content = content.replaceAll("client_id_value", clientId);
		content = content.replaceAll("client_secret_value", clientSecret);
		content = content.replaceAll("env_value", cluster.getTag());
		Files.write(path, content.getBytes(charset));
		return content;

	}

	
	public void replaceSubscriptionId(String subscriptionId) {
		try {

			BufferedReader file = new BufferedReader(new FileReader(credentialsFilePath));
			String line;
			String input = "";
			while ((line = file.readLine()) != null) {
				if (line.contains(SUBSCRIPTION_ID)) {
					line = SUBSCRIPTION_ID + "=" + subscriptionId;
				}
				input += line + '\n';
			}
			FileOutputStream File = new FileOutputStream(credentialsFilePath);
			File.write(input.getBytes());
			file.close();
			File.close();
		} catch (Exception e) {
			LOGGER.error("Cannot replace subscriptionID",e);
		}

	}

public String runPlayBook(String file) throws IOException, InterruptedException {
	ProcessBuilder builder = new ProcessBuilder("ansible-playbook",file);
		Process process= builder.start();
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		LOGGER.info(file);
		return output.toString();
	}
	
	
	public String getGeneratedFilePath() {
		return generatedFilePath;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}
	public String getTemplateStandardFilePath() {
		return templateStandardFilePath;
	}

	public void setTemplateStandardFilePath(String templateStandardFilePath) {
		this.templateStandardFilePath = templateStandardFilePath;
	}

	public String getGeneratedStandardFilePath() {
		return generatedStandardFilePath;
	}

	public void setGeneratedStandardFilePath(String generatedStandardFilePath) {
		this.generatedStandardFilePath = generatedStandardFilePath;
	}

	public String getTemplateRemoveResourceGroupFilePath() {
		return templateRemoveResourceGroupFilePath;
	}

	public void setTemplateRemoveResourceGroupFilePath(String templateRemoveResourceGroupFilePath) {
		this.templateRemoveResourceGroupFilePath = templateRemoveResourceGroupFilePath;
	}

	public String getGeneratedRemoveGroupFilePath() {
		return generatedRemoveGroupFilePath;
	}

	public void setGeneratedRemoveGroupFilePath(String generatedRemoveGroupFilePath) {
		this.generatedRemoveGroupFilePath = generatedRemoveGroupFilePath;
	}

	
}
