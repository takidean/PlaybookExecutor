package com.activeviam.creator.service.impl;

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
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Task;
import com.activeviam.creator.service.TaskService;


@Service
public class FilemanagerServiceImpl {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private String SUBSCRIPTION_ID="subscription_id";
	private String AKS_CLUSTER_CREATION="creation aks cluster";
	private String RESOURCE_GROUP_VALUE="resource_group_value";
	private String STANDARD="standard";
	private String AKS_NAME="aks_name_value";
	private String SSH_PATH="/.ssh/id_rsa.pub";
	private String SSH_RSA_VALUE="ssh-rsa_value";
	private String CLIENT_ID_VALUE="client_id_value";
	private String CLIENT_SECRET_VALUE="client_secret_value";
	private String COUNT_VALUE="count_value";
	private String VM_SIZE_VALUE="vm_size_value";
	private String ENV_VALUE="env_value";
	private String DATE_FORMAT="yyyy-MM-dd";
	
	@Autowired
	TaskService taskService;
	
	
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
	
	@Value("${createresourcegroup.template.path}")
	String templateCreationResourceGroupFilePath;

	@Value("${createresourcegroup.generatedfile.path}")
	String generatedCreationGroupFilePath;

	@Value("${logs.path}")
	String logsPath;
	
	public String replaceFileContent(Path path, Cluster cluster) throws IOException {
				
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName()+"_"+cluster.getTag());
		content = content.replaceAll(AKS_NAME, cluster.getAksName());
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+SSH_PATH)));  
		content = content.replaceAll(SSH_RSA_VALUE, ssh_key);
		content = content.replaceAll(CLIENT_ID_VALUE, clientId);
		content = content.replaceAll(CLIENT_SECRET_VALUE, clientSecret);
		content = content.replaceAll(COUNT_VALUE, cluster.getVmCount());
		content = content.replaceAll(VM_SIZE_VALUE, cluster.getVmSize());
		content = content.replaceAll(ENV_VALUE, cluster.getTag());
		Files.write(path, content.getBytes(charset));
		return content;

	}
	
	public String replaceResourceGroupCreationFileContent(Path path, Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+SSH_PATH)));  
		content = content.replaceAll(SSH_RSA_VALUE, ssh_key);
		content = content.replaceAll(CLIENT_ID_VALUE, clientId);
		content = content.replaceAll(CLIENT_SECRET_VALUE, clientSecret);		
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName()+"_"+cluster.getTag());

		Files.write(path, content.getBytes(charset));
		return content;
	}
	
	
	public String standardAksCreator(Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedStandardFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName()+"_"+cluster.getTag());
		content = content.replaceAll(AKS_NAME, STANDARD+cluster.getAksName());
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+SSH_PATH)));  
		content = content.replaceAll(SSH_RSA_VALUE, ssh_key);
		content = content.replaceAll(CLIENT_ID_VALUE, clientId);
		content = content.replaceAll(CLIENT_SECRET_VALUE, clientSecret);
		content = content.replaceAll(ENV_VALUE, cluster.getTag());
		Files.write(path, content.getBytes(charset));
		return content;
	}

	
	@Async("threadPoolTaskExecutor")
	public void asyncPlayBookCreateRG(String name) throws Exception  {
		try {
		ProcessBuilder builder = new ProcessBuilder("ansible-playbook", generatedCreationGroupFilePath);
		Process process;
		  LocalDate date = LocalDate.now();
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		  String text = date.format(formatter);
		  LocalDate localDate = LocalDate.parse(text, formatter);
			int id = taskService.save(new Task(name,2, AKS_CLUSTER_CREATION,localDate));

			process = builder.start();
		
		Path path = Paths.get(logsPath + "/" + id + ".txt");
		Charset charset = StandardCharsets.UTF_8;
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset));

		if (!output.toString().contains("fatal")) {
			String resultCreationAKS = asyncPlayBookCreateAKS(id, generatedFilePath, builder, path);
			if (!resultCreationAKS.toString().contains("fatal")) {
				String resultCreationAKSStandard = asyncPlayBookCreateAKS(id, generatedStandardFilePath, builder, path);
				if (!resultCreationAKSStandard.toString().contains("fatal")) {
					Task taskToUpdate= taskService.findById(id);
					taskToUpdate.setStatus(1);
					taskService.save(taskToUpdate);
				} else {
					Task taskToUpdate= taskService.findById(id);
					taskToUpdate.setStatus(0);
					taskService.save(taskToUpdate);
				}
			} else {
				Task taskToUpdate= taskService.findById(id);
				taskToUpdate.setStatus(0);
				taskService.save(taskToUpdate);
			}

		} else {
			Task taskToUpdate= taskService.findById(id);
			taskToUpdate.setStatus(0);
			taskService.save(taskToUpdate);
		}
		} catch (IOException | InterruptedException e) {

			LOGGER.error("cannot run playbook",e);
			throw new Exception("Error while running playbook command", e);
		}
	}

	@Async("threadPoolTaskExecutor")
	public String asyncPlayBookCreateAKS(int idTask,String fileToExecute,ProcessBuilder builder,Path path) throws IOException, InterruptedException {
	 builder = new ProcessBuilder("ansible-playbook",fileToExecute);
	Process process= builder.start();		
	Charset charset = StandardCharsets.UTF_8;
	StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));
		String line="";
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
		Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);
		return output.toString();
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
		
		LOGGER.error(file);
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

	public String getTemplateCreationResourceGroupFilePath() {
		return templateCreationResourceGroupFilePath;
	}

	public void setTemplateCreationResourceGroupFilePath(String templateCreationResourceGroupFilePath) {
		this.templateCreationResourceGroupFilePath = templateCreationResourceGroupFilePath;
	}

	public String getGeneratedCreationGroupFilePath() {
		return generatedCreationGroupFilePath;
	}

	public void setGeneratedCreationGroupFilePath(String generatedCreationGroupFilePath) {
		this.generatedCreationGroupFilePath = generatedCreationGroupFilePath;
	}

	public String getLogsPath() {
		return logsPath;
	}

	public void setLogsPath(String logsPath) {
		this.logsPath = logsPath;
	}


}
