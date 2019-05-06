package com.hellokoding.auth.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.Cluster;

@Service
public class FilemanagerServiceImpl {

	@Value("${client_id}")
	String clientId;
	
	@Value("${client_secret}")
	String clientSecret;

	@Value("${generatedfile.path}")
	String generatedFilePath;
	
	@Value("${template.path}")
	String templateFilePath;

	public String replaceFileContent(Path path, Cluster cluster) throws IOException {
				
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("resource_group_value", cluster.getGroupName());
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
	
	
	public void runPlayBook() throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder("ansible-playbook",generatedFilePath);
		Process process= builder.start();
		process.waitFor();
	}
	
	
	public String getGeneratedFilePath() {
		return generatedFilePath;
	}

	public String getTemplateFilePath() {
		return templateFilePath;
	}

}
