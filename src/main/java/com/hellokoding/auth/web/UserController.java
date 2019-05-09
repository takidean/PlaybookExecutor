package com.hellokoding.auth.web;

import com.hellokoding.auth.model.Cluster;
import com.hellokoding.auth.model.Developper;
import com.hellokoding.auth.service.FilemanagerServiceImpl;
import com.hellokoding.auth.service.SecurityService;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.validator.UserValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/validation")
	public ModelAndView validateSubmit(@ModelAttribute("cluster") Cluster cluster) {
		ModelAndView model = new ModelAndView();
		String resultCreation = "";
		try {
			resultCreation = filemanagerServiceImpl.runPlayBook(filemanagerServiceImpl.getGeneratedFilePath());
			System.out.println("******************** "+resultCreation);
			if (!resultCreation.contains("fatal")) {
				String resultCreationStandardCluster = filemanagerServiceImpl
						.runPlayBook(filemanagerServiceImpl.getGeneratedStandardFilePath());
				System.out.println("-------------------- + "+ resultCreationStandardCluster);
				resultCreation += "\n " + resultCreationStandardCluster;
				if (resultCreationStandardCluster.contains("fatal")) {
					filemanagerServiceImpl.removeCreatedResourceGroup();
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

    
    @PostMapping("/createcluster")
    public String  startSubmit(@ModelAttribute("cluster") Cluster cluster)  {
    	try {
    		filemanagerServiceImpl.replaceSubscriptionId(cluster.getSubscriptionId());
    		
	    	File copied = new File(filemanagerServiceImpl.getGeneratedFilePath());
			FileUtils.copyFile(new File(filemanagerServiceImpl.getTemplateFilePath()), copied);
			filemanagerServiceImpl.replaceFileContent(Paths.get(filemanagerServiceImpl.getGeneratedFilePath()), cluster);
 		
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
