package com.activeviam.creator.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Refresh;
import com.activeviam.creator.model.Task;
import com.activeviam.creator.service.DeveloperService;
import com.activeviam.creator.service.TaskService;
import com.activeviam.creator.service.impl.FilemanagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/createcluster")
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
   @Autowired
    private DeveloperService developerService;


    @Autowired
    private TaskService taskService;
    

	@Autowired
	FilemanagerServiceImpl filemanagerServiceImpl;

    @GetMapping({"/home"})
    public String home(Model model,Principal principal) {
    	if(developerService.validateDeveloper(principal.getName())) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
        }
   	else {
   		return "AccessDenied";
   		}
    }

    @PostMapping({"/home"})
    public String reverthome(Model model,Principal principal) {
     	if(developerService.validateDeveloper(principal.getName())) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
    	}
      	else {
       		return "AccessDenied";
       	}
    }
    
    @GetMapping({""})
    public String welcome(Model model,Principal principal) {
    	if(developerService.validateDeveloper(principal.getName())) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
        }
   	else {
   		return "AccessDenied";
   		}
    }
    
    @PostMapping({"/revert"})
    public String revert(Model model,Principal principal) {
     	if(developerService.validateDeveloper(principal.getName())) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
    	}
      	else {
       		return "AccessDenied";
       	}
    }

    @GetMapping("/result")
    public String runCreation() {
		try {
		filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedAKSFilePath());
		} catch (IOException | InterruptedException e) {
		return "fail";	
		}
    	return "result";    	
    }
  
    
  @GetMapping("/taskslist")
	public String tasksList(Model model, Principal principal) {
		if (developerService.validateDeveloper(principal.getName())) {
			ArrayList<Task> tsls = new ArrayList<Task>();
			tsls.addAll(taskService.findTaskStatusByLocalDate());

			ObjectMapper mapper = new ObjectMapper();
			String json = "";
			try {
				json = mapper.writeValueAsString(tsls);
			} catch (Exception e) {
				LOGGER.error("a problem with your task List ", e);
			}
			model.addAttribute("list", json);
			model.addAttribute("tasks", tsls);

			return "taskslist";
		} else {
			return "AccessDenied";
		}
	}

  @GetMapping("/taskslistrefresh")
  @ResponseBody
	public List<Task> taskslistrefresh(Model model) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.addAll(taskService.findTaskStatusByLocalDate());
		return taskList;
	}
  
  @GetMapping("/logs/{id}")
	public String taskslogs(@PathVariable(value = "id") String id, Model model, Principal principal) {
		if (developerService.validateDeveloper(principal.getName())) {
			String resultLogs = "no logs found";
			try {
				BufferedReader file = new BufferedReader(
						new FileReader(filemanagerServiceImpl.getLogsPath() + "/" + id + ".txt"));
				String line;
				String input = "";
				while ((line = file.readLine()) != null) {
					input += "<h5>" + line + "</h5>" + '\n';
				}
				resultLogs = input;
				file.close();
			} catch (Exception e) {
				LOGGER.error("Cannot replace subscriptionID", e);
			}
			model.addAttribute("logs", resultLogs);
			return "result";
		} else {
			return "AccessDenied";
		}
	}
  
    @PostMapping("/validation")
	public String validateSubmit( Principal principal) throws Exception {
		if (developerService.validateDeveloper(principal.getName())) {
			try {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				filemanagerServiceImpl.asyncPlayBookCreateRG(filemanagerServiceImpl.getCluster(),auth.getName());
			} catch (IOException | InterruptedException e) {
				LOGGER.error("cannot run playBook", e);
				throw new Exception("cannot run", e);
			}
			return "launched";
		} else {
			return "AccessDenied";}
  }
  

    
    @MessageMapping("/hello")
    @SendTo("/topic/tasks")
    public Refresh reportCurrentTime() throws InterruptedException {
    	int numberOfTasks =taskService.findTaskStatusByLocalDateAndStatus(2).size();
        return new Refresh("Tasks " + HtmlUtils.htmlEscape(""+numberOfTasks+""));
    }

    public void fileGenerator(String filePath,String templatefile) throws IOException {
    	File copied = new File(filePath);
		FileUtils.copyFile(new File(templatefile), copied);
    }
  
    
    @PostMapping("/createcluster")
    public String  startSubmit(@ModelAttribute("cluster") Cluster cluster,Principal principal,@RequestParam("ssl_certificate") MultipartFile ssl_certificate, @RequestParam("key") MultipartFile key )  {
     	if(developerService.validateDeveloper(principal.getName())) {
    	try {
            filemanagerServiceImpl.setCluster(cluster);
            
    		filemanagerServiceImpl.replaceSubscriptionId(cluster.getSubscriptionId());
    		
			fileGenerator(filemanagerServiceImpl.getGeneratedAKSFilePath(), filemanagerServiceImpl.getTemplateAKSFilePath());
			filemanagerServiceImpl.replaceFileContent(Paths.get(filemanagerServiceImpl.getGeneratedAKSFilePath()), cluster);
	    	
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationGroupFilePath(), filemanagerServiceImpl.getTemplateCreationResourceGroupFilePath());
			filemanagerServiceImpl.replaceResourceGroupCreationFileContent(Paths.get(filemanagerServiceImpl.getGeneratedCreationGroupFilePath()), cluster);

			fileGenerator(filemanagerServiceImpl.getGeneratedStandardFilePath(), filemanagerServiceImpl.getTemplateStandardFilePath());
			filemanagerServiceImpl.standardAksCreator(cluster);
			
			//ServerDB
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationdbserverFilePath(), filemanagerServiceImpl.getTemplateCreationdbserverFilePath());
			filemanagerServiceImpl.replaceDbServerFileContent(Paths.get(filemanagerServiceImpl.getGeneratedCreationdbserverFilePath()), cluster);

			// DB file path
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationdbFilePath(), filemanagerServiceImpl.getTemplateCreationdbFilePath());
			filemanagerServiceImpl.replaceDbFileContent(Paths.get(filemanagerServiceImpl.getGeneratedCreationdbFilePath()), cluster);
			// keycloak file 
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationkeycloakFilePath(), filemanagerServiceImpl.getTemplateCreationkeycloakFilePath());
			filemanagerServiceImpl.replaceKeycloakDeploymentFileContent(cluster);
			// keycloak secret
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationkeycloakSecretFilePath(), filemanagerServiceImpl.getTemplateCreationkeycloakSecretFilePath());
			filemanagerServiceImpl.createSecretKeycloak(cluster);
			// db secret
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationSecretDBFilePath(), filemanagerServiceImpl.getTemplateCreationSecretDBFilePath());
			filemanagerServiceImpl.createSecretDB(cluster);

			// INGRESS  
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationIngress(), filemanagerServiceImpl.getTemplateCreationIngress());
			filemanagerServiceImpl.createIngress(cluster);

			// keycloak  
			fileGenerator(filemanagerServiceImpl.getGeneratedCreationKeycloak(), filemanagerServiceImpl.getTemplateCreationKeycloak());
			filemanagerServiceImpl.createKeycloak(cluster);

			// certif file 
	            byte[] bytes = ssl_certificate.getBytes();
	            Path path = Paths.get(filemanagerServiceImpl.getCertFilePath() + ssl_certificate.getOriginalFilename());
	            Files.write(path, bytes);

	             bytes = key.getBytes();
	              path = Paths.get(filemanagerServiceImpl.getKeyFilePath() + key.getOriginalFilename());
	            Files.write(path, bytes);
	 
	            filemanagerServiceImpl.setCluster(cluster);
    	} catch (IOException e) {
			LOGGER.error("a problem with your file ", e);
 		}
        return "validation";
    	}  else {
	   		return "AccessDenied";
	   	}
    }
    
    
}
