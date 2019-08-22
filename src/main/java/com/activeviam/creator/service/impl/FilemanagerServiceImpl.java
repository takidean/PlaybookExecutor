package com.activeviam.creator.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Task;
import com.activeviam.creator.service.TaskService;
import com.activeviam.creator.service.common.Utils;


@Service
@PropertySource("file:/opt/builder/external.properties")
public class FilemanagerServiceImpl {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private String SUBSCRIPTION_ID="subscription_id";
	private String AKS_CLUSTER_CREATION="creation aks cluster";
	private String RESOURCE_GROUP_VALUE="resource_group_value";
	private String AKS_NAME="aks_name_value";
	private String SSH_PATH="/.ssh/id_rsa.pub";
	private String SSH_RSA_VALUE="ssh-rsa_value";
	private String CLIENT_ID_VALUE="client_id_value";
	private String CLIENT_SECRET_VALUE="client_secret_value";
	private String COUNT_VALUE="count_value";
	private String VM_SIZE_VALUE="vm_size_value";
	private String ENV_VALUE="env_value";
	private String DATE_FORMAT="yyyy-MM-dd";
	private String DB_SERVER_NAME ="db_server_name";
	private String ADMIN_UN ="admin_user_name";
	private String ADMIN_PWD ="admin_pwd";
	private String DB_NAME ="db_name";
	private String JENKINS_URL="https://retailplatform.cloud.activeviam.com/jenkins";
	private String DB_USERNAME_SECRET="value_username_db";
	private String DB_USERNAME_PASSWORD="value_password_db";
	
	private String KEYCLOAK_USERNAME_SECRET="value_username_keycloak";
	private String KEYCLOAK_USERNAME_PASSWORD="value_password_keycloak";
	private String DOMAIN_VALUE_NAME="domain_name_value";
	private String GITHUB_REPO="github_repo";
	private String BRANCHE="branche";
	@Autowired
	TaskService taskService;
	
	
	@Value("${client_id}")
	String clientId;
	
	@Value("${tenant_id}")
	String tenantId;

	@Value("${client_secret}")
	String clientSecret;

	@Value("${generatedfile.path}")
	String generatedAKSFilePath;
	
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

	// db server
	@Value("${dbserver.template.path}")
	String templateCreationdbserverFilePath;

	@Value("${dbserver.generatedfile.path}")
	String generatedCreationdbserverFilePath;

	// db 
	@Value("${db.template.path}")
	String templateCreationdbFilePath;

	@Value("${db.generatedfile.path}")
	String generatedCreationdbFilePath;

	// keycloak deployment
	@Value("${keycloak.template.path}")
	String templateCreationkeycloakFilePath;

	@Value("${keycloak.generatedfile.path}")
	String generatedCreationkeycloakFilePath;
	
	// keycloak admin secret
	@Value("${keycloaksecret.template.path}")
	String templateCreationKeycloakSecretFilePath;

	@Value("${keycloaksecret.generatedfile.path}")
	String generatedCreationKeycloakSecretFilePath;

	// db secret
	@Value("${dbsecret.template.path}")
	String templateCreationSecretDBFilePath;

	@Value("${dbsecret.generatedfile.path}")
	String generatedCreationSecretDBFilePath;

	@Value("${key.generatedfile.path}")
	String keyFilePath;

	@Value("${cert.generatedfile.path}")
	String certFilePath;
	
	@Value("${pvc.keycloak.path}")
 	String keycloakCreationPvc;
	
	@Value("${ingress.template.path}")
	String templateCreationIngress;

	@Value("${ingress.generatedfile.path}")
	String generatedCreationIngress;

	@Value("${keycloak.template.path}")
	String templateCreationKeycloak;

	@Value("${keycloak.generatedfile.path}")
	String generatedCreationKeycloak;

	@Value("${dbAdminUsername}")
	String dbAdminUsername;

	@Value("${keycloakUser}")
	String keycloakUser;

	@Value("${dockerUserName}")
	String dockerUserName;

	@Value("${dockerEmail}")
	String dockerEmail;

	@Value("${storageclass.generatedfile.path}")
	String storageClass;
	
	@Value("${keycloakservice.generatedfile.path}")
	String fileKeycloakServicePath;
	
	@Value("${logs.path}")
	String logsPath;

	@Value("${config.xml.path}")
	String configFile;

	@Value("${generated.config.xml.path}")
	String generatedConfigFile;
	
	@Value("${jenkinsUser.username}")
	String jenkinsUserName;
	
	@Value("${jenkinsUser.token}")
	String jenkinsUserToken;
	
	Cluster cluster;
	
	
	
	public String replaceGithubBranch( Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedConfigFile);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(GITHUB_REPO, cluster.getGithubRepository());
		content = content.replaceAll(BRANCHE, cluster.getGithubBranch());
		Files.write(path, content.getBytes(charset));
		return content;
	}
	
	public String replaceFileContent(Path path, Cluster cluster) throws IOException {
				
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName());
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
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName());
		Files.write(path, content.getBytes(charset));
		return content;
	}
	
	// DB server file path .
	public String replaceDbServerFileContent(Path path, Cluster cluster) throws IOException {
		
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName());
		content = content.replaceAll(DB_SERVER_NAME, cluster.getDbServerName());
		content = content.replaceAll(ADMIN_UN, cluster.getDbAdminUsername());
		content = content.replaceAll(ADMIN_PWD, cluster.getDbAdminPassword());
		Files.write(path, content.getBytes(charset));
		return content;

	}
	
	// DB file generator
	public String replaceDbFileContent(Path path, Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName());
		content = content.replaceAll(DB_SERVER_NAME, cluster.getDbServerName());	
		content = content.replaceAll(DB_NAME, cluster.getDbName());		
		Files.write(path, content.getBytes(charset));
		return content;
	}
	
	//create standard aks cluster
	public String standardAksCreator(Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedStandardFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(RESOURCE_GROUP_VALUE, cluster.getAksName());
		content = content.replaceAll(AKS_NAME, cluster.getStandardAksName());
		String ssh_key= new String(Files.readAllBytes(Paths.get(System.getProperty("user.home")+SSH_PATH)));  
		content = content.replaceAll(SSH_RSA_VALUE, ssh_key);
		content = content.replaceAll(CLIENT_ID_VALUE, clientId);
		content = content.replaceAll(CLIENT_SECRET_VALUE, clientSecret);
		content = content.replaceAll(ENV_VALUE, cluster.getTag());
		Files.write(path, content.getBytes(charset));
		return content;
	}

	// keycloak file generator
	public String replaceKeycloakDeploymentFileContent(Cluster cluster) throws IOException  {
		 
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedCreationkeycloakFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replace(DB_SERVER_NAME, cluster.getDbServerName());
		
		content = content.replaceAll(DB_NAME, cluster.getDbName());
	
		try (Writer writer = new BufferedWriter(new FileWriter(path.toString()))) {
		    writer.write(content);
		} catch (IOException e) {
		    e.printStackTrace();
		}//
			
		 content = new String(Files.readAllBytes(path), charset);
		 return content;
	 
	}
	
	@Async("threadPoolTaskExecutor")
	public void asyncPlayBookCreateRG(Cluster clusterComplete,String name) throws Exception  {
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
			String resultCreationAKS = asyncPlayBookCreateAKS(id, generatedAKSFilePath, path);
			if (!resultCreationAKS.toString().contains("fatal")) {
				String resultCreationAKSStandard = asyncPlayBookCreateAKS(id, generatedStandardFilePath, path);
				if (!resultCreationAKSStandard.toString().contains("fatal")) {

					String resultCreationDBStandard = asyncPlayBookCreateAKS(id, generatedCreationdbserverFilePath, path);
					if(!resultCreationDBStandard.toString().contains("fatal") || !resultCreationDBStandard.toString().contains("ERROR")) {
						String resultCreationDB = asyncPlayBookCreateAKS(id, generatedCreationdbFilePath, path);
					if(!resultCreationDB.contains("fatal")) {
						Task taskToUpdate= taskService.findById(id);
						taskToUpdate.setStatus(1);
						taskService.save(taskToUpdate);
						runDeploymentScripts(clusterComplete, id);
					}
					else {
						Task taskToUpdate= taskService.findById(id);
 						taskToUpdate.setStatus(0);
						taskService.save(taskToUpdate);
					}
					}else {
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
	public String asyncPlayBookCreateAKS(int idTask,String fileToExecute,Path path) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder("ansible-playbook",fileToExecute);
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

	//connect az user to aks cluster
	@Async("threadPoolTaskExecutor")
	public void connectUser(Cluster cluster,int taskid) throws IOException, InterruptedException {
		
	String login=	"az login --service-principal --username "+clientId+ " --password "+clientSecret+" --tenant "+tenantId;		
	ProcessBuilder builder = new ProcessBuilder();
	builder.command("bash", "-c",login);
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
	
	String mergeaks ="az aks get-credentials --resource-group " +cluster.getAksName()+ " --name " +cluster.getStandardAksName()+ " --subscription "+cluster.getSubscriptionId();
 	ProcessBuilder builderMerge = new ProcessBuilder();
	builderMerge.command("bash", "-c",mergeaks);
	Process processMerge= builderMerge.start();
	BufferedReader readerMerge = new BufferedReader(
			new InputStreamReader(processMerge.getInputStream()));	

 	while ((line = readerMerge.readLine()) != null) {
		output.append(line + "\n");
	}
	Files.write(path, output.toString().getBytes(charset),StandardOpenOption.APPEND);
	}
	
	// start deploying
	public void runDeploymentScripts(Cluster cluster,int taskid) throws IOException, InterruptedException, URISyntaxException {
 		 connectUser(cluster,taskid);
		 Utils.applyKubeFiles(generatedCreationSecretDBFilePath, taskid, logsPath);
		 Utils.applyKubeFiles(generatedCreationKeycloakSecretFilePath,taskid, logsPath);
		 Utils.applyKubeFiles(storageClass, taskid, logsPath);
		 Utils.applyKubeFiles(fileKeycloakServicePath, taskid, logsPath);
		 Utils.createSecretDocker(cluster, taskid,logsPath);
		 Utils.createNginx(generatedCreationIngress, taskid, logsPath);
		 Utils.applyNginxController(generatedCreationIngress, taskid, logsPath);
		 Utils.deployKeycloak(generatedCreationKeycloak, keycloakCreationPvc, taskid, logsPath);
		 Utils.changeFirewallIpAddress(cluster, taskid, logsPath);
 		 Utils.createDomaineNameTlsCert(certFilePath, keyFilePath, taskid, logsPath);
 		 Utils.helmInstall( taskid, logsPath);
		 Utils.deployKeycloakPod(generatedCreationKeycloak, keycloakCreationPvc, taskid, logsPath);
	     Utils.connectJenkins(generatedConfigFile, jenkinsUserName, jenkinsUserToken, cluster.getAksName(),JENKINS_URL);
	}
	
		
	// subsctiption Id
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
			LOGGER.error("Cannot replace subscriptionID", e);
		}
	}

	// generate secrets from base 64
	public String generateSecrets( String pwd) throws IOException{
        Base64.Encoder encoder = Base64.getEncoder();  
		String line=encoder.encodeToString(pwd.getBytes());
		return line;
	}
	
	 // secret keycloak
	public String createSecretKeycloak(Cluster cluster) throws IOException {
		String userBase =generateSecrets(cluster.getKeycloakUser());
		String pwdBase = generateSecrets(cluster.getKeycloakPassword());
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedCreationKeycloakSecretFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(KEYCLOAK_USERNAME_SECRET, userBase);
		content = content.replaceAll(KEYCLOAK_USERNAME_PASSWORD, pwdBase);
		Files.write(path, content.getBytes(charset));
		return content;	
	}
	
	// secret DB
	public String createSecretDB(Cluster cluster) throws IOException {
		String userBase =generateSecrets(cluster.getDbAdminUsername());
		String pwdBase = generateSecrets(cluster.getDbAdminPassword());
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedCreationSecretDBFilePath);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(DB_USERNAME_SECRET, userBase);
		content = content.replaceAll(DB_USERNAME_PASSWORD, pwdBase);
		Files.write(path, content.getBytes(charset));
		return content;	
	}

	
	public String runPlayBook(String file) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder("ansible-playbook", file);
		Process process = builder.start();
		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		LOGGER.error(file);
		return output.toString();
	}
		
	//create ingress yaml file 
	public void createIngress(Cluster cluster) throws IOException {
		Charset charset = StandardCharsets.UTF_8;
		Path path=Paths.get(generatedCreationIngress);
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(DOMAIN_VALUE_NAME, cluster.getDomainName());
 		Files.write(path, content.getBytes(charset));
	}

	
	public String getGeneratedAKSFilePath() {
		return generatedAKSFilePath;
	}

	public String getTemplateAKSFilePath() {
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

	public String getTemplateCreationdbserverFilePath() {
		return templateCreationdbserverFilePath;
	}

	public void setTemplateCreationdbserverFilePath(String templateCreationdbserverFilePath) {
		this.templateCreationdbserverFilePath = templateCreationdbserverFilePath;
	}

	public String getGeneratedCreationdbserverFilePath() {
		return generatedCreationdbserverFilePath;
	}

	public void setGeneratedCreationdbserverFilePath(String generatedCreationdbserverFilePath) {
		this.generatedCreationdbserverFilePath = generatedCreationdbserverFilePath;
	}

	public String getTemplateCreationdbFilePath() {
		return templateCreationdbFilePath;
	}

	public void setTemplateCreationdbFilePath(String templateCreationdbFilePath) {
		this.templateCreationdbFilePath = templateCreationdbFilePath;
	}

	public String getGeneratedCreationdbFilePath() {
		return generatedCreationdbFilePath;
	}

	public void setGeneratedCreationdbFilePath(String generatedCreationdbFilePath) {
		this.generatedCreationdbFilePath = generatedCreationdbFilePath;
	}

	public String getTemplateCreationkeycloakFilePath() {
		return templateCreationkeycloakFilePath;
	}

	public void setTemplateCreationkeycloakFilePath(String templateCreationkeycloakFilePath) {
		this.templateCreationkeycloakFilePath = templateCreationkeycloakFilePath;
	}

	public String getGeneratedCreationkeycloakFilePath() {
		return generatedCreationkeycloakFilePath;
	}

	public void setGeneratedCreationkeycloakFilePath(String generatedCreationkeycloakFilePath) {
		this.generatedCreationkeycloakFilePath = generatedCreationkeycloakFilePath;
	}

	public String getTemplateCreationkeycloakSecretFilePath() {
		return templateCreationKeycloakSecretFilePath;
	}

	public void setTemplateCreationkeycloakSecretFilePath(String templateCreationkeycloakSecretFilePath) {
		this.templateCreationKeycloakSecretFilePath = templateCreationkeycloakSecretFilePath;
	}

	public String getGeneratedCreationkeycloakSecretFilePath() {
		return generatedCreationKeycloakSecretFilePath;
	}

	public void setGeneratedCreationkeycloakSecretFilePath(String generatedCreationkeycloakSecretFilePath) {
		this.generatedCreationKeycloakSecretFilePath = generatedCreationkeycloakSecretFilePath;
	}

	public String getTemplateCreationSecretDBFilePath() {
		return templateCreationSecretDBFilePath;
	}

	public void setTemplateCreationSecretDBFilePath(String templateCreationSecretDBFilePath) {
		this.templateCreationSecretDBFilePath = templateCreationSecretDBFilePath;
	}

	public String getGeneratedCreationSecretDBFilePath() {
		return generatedCreationSecretDBFilePath;
	}

	public void setGeneratedCreationSecretDBFilePath(String generatedCreationSecretDBFilePath) {
		this.generatedCreationSecretDBFilePath = generatedCreationSecretDBFilePath;
	}

	public String getKeyFilePath() {
		return keyFilePath;
	}

	public void setKeyFilePath(String keyFilePath) {
		this.keyFilePath = keyFilePath;
	}

	public String getCertFilePath() {
		return certFilePath;
	}

	public void setCertFilePath(String certFilePath) {
		this.certFilePath = certFilePath;
	}


	public String getTemplateCreationKeycloak() {
		return templateCreationKeycloak;
	}

	public void setTemplateCreationKeycloak(String templateCreationKeycloak) {
		this.templateCreationKeycloak = templateCreationKeycloak;
	}

	public String getGeneratedCreationKeycloak() {
		return generatedCreationKeycloak;
	}

	public void setGeneratedCreationKeycloak(String generatedCreationKeycloak) {
		this.generatedCreationKeycloak = generatedCreationKeycloak;
	}

	public String getTemplateCreationIngress() {
		return templateCreationIngress;
	}

	public void setTemplateCreationIngress(String templateCreationIngress) {
		this.templateCreationIngress = templateCreationIngress;
	}

	public String getGeneratedCreationIngress() {
		return generatedCreationIngress;
	}

	public void setGeneratedCreationIngress(String generatedCreationIngress) {
		this.generatedCreationIngress = generatedCreationIngress;
	}


	
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

    public void fileGenerator(String filePath,String templatefile) throws IOException {
    	File copied = new File(filePath);
		FileUtils.copyFile(new File(templatefile), copied);
    }

	public void generateFiles(Cluster cluster) throws IOException {

		Cluster completeCluster = Utils.createCompleteClusterInformations(cluster, dbAdminUsername, keycloakUser, dockerUserName, dockerEmail);

		this.setCluster(completeCluster);
		fileGenerator(getGeneratedAKSFilePath(), getTemplateAKSFilePath());
		replaceFileContent(Paths.get(getGeneratedAKSFilePath()), completeCluster);
    	
		fileGenerator(getGeneratedCreationGroupFilePath(), getTemplateCreationResourceGroupFilePath());
		replaceResourceGroupCreationFileContent(Paths.get(getGeneratedCreationGroupFilePath()), completeCluster);

		fileGenerator(getGeneratedStandardFilePath(), getTemplateStandardFilePath());
		standardAksCreator(completeCluster);
		
		//ServerDB
		fileGenerator(getGeneratedCreationdbserverFilePath(), getTemplateCreationdbserverFilePath());
		replaceDbServerFileContent(Paths.get(getGeneratedCreationdbserverFilePath()), completeCluster);

		// DB file path
		fileGenerator(getGeneratedCreationdbFilePath(), getTemplateCreationdbFilePath());
		replaceDbFileContent(Paths.get(getGeneratedCreationdbFilePath()),completeCluster);
		// keycloak file 
		fileGenerator(getGeneratedCreationkeycloakFilePath(), getTemplateCreationkeycloakFilePath());
		replaceKeycloakDeploymentFileContent(completeCluster);
		// keycloak secret
		fileGenerator(getGeneratedCreationkeycloakSecretFilePath(), getTemplateCreationkeycloakSecretFilePath());
		createSecretKeycloak(completeCluster);
		// db secret
		fileGenerator(generatedCreationSecretDBFilePath, templateCreationSecretDBFilePath);
		createSecretDB(completeCluster);

		// INGRESS  
		fileGenerator(getGeneratedCreationIngress(), getTemplateCreationIngress());
		createIngress(completeCluster);

		// github jenkins config file
		fileGenerator(generatedConfigFile , configFile);
		replaceGithubBranch(cluster);
	}



}
