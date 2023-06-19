package de.materna.date4u.interfaces.controller.web;

import de.materna.date4u.core.entity.Photo;
import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.core.service.PhotoService;
import de.materna.date4u.interfaces.formData.ProfileFormData;
import de.materna.date4u.interfaces.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Controller
public class OpenAccessController {

    private static final Logger logger = LoggerFactory.getLogger(OpenAccessController.class);

    private final ProfileController profileController;

    public OpenAccessController(ProfileController profileController) {
        this.profileController = profileController;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        logger.debug("Requested register page");
        model.addAttribute("profile", new ProfileFormData());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute(value = "profile") Optional<ProfileFormData> profileFormDataOptional,
                           @RequestParam("image") MultipartFile file) {
        logger.debug("Received " + profileFormDataOptional.toString() + "\n" + file);


        if (profileFormDataOptional.isPresent()) {
            profileController.saveProfile(profileFormDataOptional, file);
            logger.info("Registered Profile " + profileFormDataOptional.get());
            return "redirect:/home";
        } else {
            logger.warn("Did not register profile");
            return "redirect:/login";
        }
    }
}
