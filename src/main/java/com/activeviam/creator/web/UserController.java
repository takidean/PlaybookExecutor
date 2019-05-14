package com.activeviam.creator.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.activeviam.creator.model.Cluster;
import com.activeviam.creator.model.Developper;
import com.activeviam.creator.model.Greeting;
import com.activeviam.creator.service.SecurityService;
import com.activeviam.creator.service.UserService;
import com.activeviam.creator.service.impl.FilemanagerServiceImpl;
import com.activeviam.creator.validator.UserValidator;

@Controller
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

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

    
    @PostMapping("/validation")
	public void validateSubmit(@ModelAttribute("cluster") Cluster cluster) {
 		try {
			filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedFilePath());
		} catch (IOException | InterruptedException e) {
 LOGGER.debug("cannot run playBook", e);;
		}
 	}
    
    @MessageMapping("/hello")
    @SendTo("/topic/tasks")
    public Greeting reportCurrentTime() throws InterruptedException {
        return new Greeting("Tasks " + HtmlUtils.htmlEscape(""+2+""));
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


    	} catch (IOException e) {
			LOGGER.error("a problem with your file ", e);
 		}
        return "validation";
    }
}
