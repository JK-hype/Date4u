package de.materna.date4u.interfaces.controller.web;

import de.materna.date4u.core.service.LikesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;


@Controller
public class LikeController {

    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    private final LikesService likesService;

    public LikeController(LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping("/likes/toggle/{nickname}")
    public String toggleLikes(Principal principal, @PathVariable String nickname) {
        logger.debug("Received " + nickname);
        boolean success = likesService.toggleLike(principal.getName(), nickname);
        logger.info(success ? "Did toggle for " + nickname + " and id" : "Could not toggle for " + nickname + " and id");
        return "redirect:/profiles?id=" + nickname;
    }
}
