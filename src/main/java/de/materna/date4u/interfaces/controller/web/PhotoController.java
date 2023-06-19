package de.materna.date4u.interfaces.controller.web;

import de.materna.date4u.core.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @DeleteMapping("/photos/delete/{id}")
    public String delete(Principal principal, @PathVariable Long id) {
        logger.debug("Received " + id);
        photoService.deletePhotoById(id, principal.getName());
        return "redirect:/profile";
    }

    @PutMapping(path = "photos/profile-photo/{id}")
    public String setProfilePhoto(Principal principal, @PathVariable Long id) {
        logger.debug("Received " + id);
        photoService.setPhotoAsProfilePhoto(id, principal.getName());
        return "redirect:/profile";
    }
}
