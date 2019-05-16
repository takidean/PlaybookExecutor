package com.activeviam.creator.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Developper;
import com.activeviam.creator.service.FilemanagerServiceImpl;
import com.activeviam.creator.service.SecurityService;
import com.activeviam.creator.service.UserService;
import com.activeviam.creator.validator.UserValidator;
import org.springframework.web.util.HtmlUtils;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Developper;
import com.activeviam.creator.model.Greeting;
import com.activeviam.creator.model.Task;
import com.activeviam.creator.service.SecurityService;
import com.activeviam.creator.service.TaskService;
import com.activeviam.creator.service.UserService;
import com.activeviam.creator.service.impl.FilemanagerServiceImpl;
import com.activeviam.creator.validator.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserValidator userValidator;

	@Autowired
	FilemanagerServiceImpl filemanagerServiceImpl;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new Developper());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") Developper userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/createcluster";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password are invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping({"/", "/createcluster"})
    public String welcome(Model model) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
    }
    
    @PostMapping({"/"})
    public String revert(Model model) {
    	model.addAttribute("cluster", new Cluster());
        return "createcluster";
    }

    @GetMapping("/result")
    public String login() {
		try {
		filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedFilePath());
		} catch (IOException | InterruptedException e) {
		return "fail";	
		}
    	return "result";    	
    }

    @PostMapping("/validation")
	public ModelAndView validateSubmit(@ModelAttribute("cluster") Cluster cluster) {
		ModelAndView model = new ModelAndView();
		String resultCreation = "";
		try {
			resultCreation = filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedFilePath());
			if (!resultCreation.contains("fatal")) {
				String resultCreationStandardCluster = filemanagerServiceImpl
						.runPlayBook(filemanagerServiceImpl.getGeneratedStandardFilePath());
				resultCreation += "\n " + resultCreationStandardCluster;
				if (resultCreationStandardCluster.contains("fatal")) {
					String result =filemanagerServiceImpl.removeCreatedResourceGroup();
					System.out.println(result);
					model.setViewName("failCreationStandardCluster");
					model.addObject("message", resultCreationStandardCluster);
					return model;
				}
			} else {
				filemanagerServiceImpl.removeCreatedResourceGroup();
				model.setViewName("fail");
				model.addObject("message", resultCreation);
				return model;
			}
		} catch (IOException | InterruptedException e) {
			model.addObject("message", e.getMessage());
			model.setViewName("fail");
			return model;
		}
		model.addObject("message", resultCreation);
		model.setViewName("result");
		return model;
	}
//    @PostMapping("/validation")
//	public ModelAndView validateSubmit(@ModelAttribute("cluster") Cluster cluster) {
//		ModelAndView model = new ModelAndView();
//		String resultCreation = "";
//		try {
//			resultCreation = filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedFilePath());
//
//		} catch (IOException | InterruptedException e) {
//			model.addObject("message", e.getMessage());
//			model.setViewName("fail");
//			return model;
//		}
//		model.addObject("message", resultCreation);
//		model.setViewName("result");
//		return model;
//	}

    
    
  @GetMapping("/taskslist")
	public String tasksList(Model model) {
	  Task t = new Task(1,"Tas",1,"creating");
	  Task t2 = new Task(2,"Tas",2,"updat");
//	  model.addAttribute("tasks", taskService.findTaskStatusByLocalDate());
	  ArrayList<Task> tsls=new ArrayList<Task>();
	  tsls.add(t);
	  tsls.add(t2);
	  
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(tsls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	  model.addAttribute("list", json);

	  model.addAttribute("tasks",tsls );

	  return "taskslist";
  }

  @GetMapping("/taskslistrefresh")
  @ResponseBody
	public List<Task> taskslistrefresh(Model model) {
	  Task t = new Task(1,"Tas",1,"creating");
	  Task t2 = new Task(2,"Tas",2,"updat");
	  ArrayList<Task> tsls=new ArrayList<Task>();
	  tsls.add(t);
	  tsls.add(t2);
	  return tsls;
  }
    
    @PostMapping("/validation")
	public void validateSubmit(@ModelAttribute("cluster") Cluster cluster) throws Exception {
		try {
			filemanagerServiceImpl.asyncPlayBookCreateRG();
		} catch (IOException | InterruptedException e) {
			LOGGER.error("cannot run playBook", e);
			throw new Exception("cannot run",e);
		}
	}
    
    @MessageMapping("/hello")
    @SendTo("/topic/tasks")
    public Greeting reportCurrentTime() throws InterruptedException {
    	
    	int numberOfTasks =taskService.findTaskStatusByLocalDate().size();
  
        return new Greeting("Tasks " + HtmlUtils.htmlEscape(""+numberOfTasks+""));
    }

//    @Scheduled(fixedRate = 10000)
//    public void lunchAction() throws InterruptedException {
//    	reportCurrentTime();
//    }
    
    
    @PostMapping("/createcluster")
    public String  startSubmit(@ModelAttribute("cluster") Cluster cluster)  {
    	try {
    		filemanagerServiceImpl.replaceSubscriptionId(cluster.getSubscriptionId());
    		
	    	File copied = new File(filemanagerServiceImpl.getGeneratedFilePath());
			FileUtils.copyFile(new File(filemanagerServiceImpl.getTemplateFilePath()), copied);
			filemanagerServiceImpl.replaceFileContent(Paths.get(filemanagerServiceImpl.getGeneratedFilePath()), cluster);
	    	File copiedCreationRG = new File(filemanagerServiceImpl.getGeneratedCreationGroupFilePath());
			FileUtils.copyFile(new File(filemanagerServiceImpl.getTemplateCreationResourceGroupFilePath()), copiedCreationRG);
			filemanagerServiceImpl.replaceResourceGroupCreationFileContent(Paths.get(filemanagerServiceImpl.getGeneratedCreationGroupFilePath()), cluster);

	    	File copiedStandardFile = new File(filemanagerServiceImpl.getGeneratedStandardFilePath());
			FileUtils.copyFile(new File(filemanagerServiceImpl.getTemplateStandardFilePath() ), copiedStandardFile);
			filemanagerServiceImpl.standardAksCreator(cluster);

	    	File copiedRemovalRG = new File(filemanagerServiceImpl.getGeneratedRemoveGroupFilePath());
			FileUtils.copyFile(new File(filemanagerServiceImpl.getTemplateRemoveResourceGroupFilePath()), copiedRemovalRG);
			filemanagerServiceImpl.replaceResourceGroupRemovalFileContent(Paths.get(filemanagerServiceImpl.getGeneratedRemoveGroupFilePath()), cluster.getAksName(), cluster.getTag());

    	} catch (IOException e) {
			LOGGER.error("a problem with your file ", e);
 		}
        return "validation";
    }
}
