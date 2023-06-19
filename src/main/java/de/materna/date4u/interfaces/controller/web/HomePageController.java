package de.materna.date4u.interfaces.controller.web;

import de.materna.date4u.core.service.LikesService;
import de.materna.date4u.interfaces.formData.ProfileFormData;
import de.materna.date4u.interfaces.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomePageController {

    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

    private final LikesService likesService;
    private final ProfileMapper profileMapper;

    public HomePageController(LikesService likesService, ProfileMapper profileMapper) {
        this.likesService = likesService;
        this.profileMapper = profileMapper;
    }

    @GetMapping("/home")
    public String getHomePage(Principal principal, Model model) {
        logger.debug("Requested home page");
        List<ProfileFormData> liker = likesService.findLiker(principal.getName()).stream()
                .map(profileMapper::mapProfileToProfileFormData)
                .toList();
        logger.info("Found " + liker);
        model.addAttribute("profiles", liker);
        return "home";
    }
}
