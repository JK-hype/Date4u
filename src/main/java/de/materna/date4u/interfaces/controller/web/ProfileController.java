package de.materna.date4u.interfaces.controller.web;

import de.materna.date4u.core.entity.Photo;
import de.materna.date4u.core.entity.Profile;
import de.materna.date4u.core.filter.SearchFilter;
import de.materna.date4u.core.service.LikesService;
import de.materna.date4u.core.service.PhotoService;
import de.materna.date4u.core.service.ProfileService;
import de.materna.date4u.interfaces.formData.ProfileFormData;
import de.materna.date4u.interfaces.formData.SearchFormData;
import de.materna.date4u.interfaces.mapper.ProfileMapper;
import de.materna.date4u.interfaces.mapper.SearchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final PhotoService photoService;
    private final LikesService likesService;
    private final ProfileMapper profileMapper;
    private final SearchMapper searchMapper;

    public ProfileController(ProfileService profileService,
                             PhotoService photoService, LikesService likesService,
                             ProfileMapper profileMapper,
                             SearchMapper searchMapper) {
        this.profileService = profileService;
        this.photoService = photoService;
        this.likesService = likesService;
        this.profileMapper = profileMapper;
        this.searchMapper = searchMapper;
    }

    @GetMapping("/profiles")
    public String getProfilesPage(
            Principal principal,
            Model model,
            @RequestParam(required = false) Optional<String> nicknameOptional) {
        logger.debug("Received " + nicknameOptional);
        if (nicknameOptional.isEmpty()) {
            return getProfiles(model, principal.getName(), "my-profile");
        } else {
            String nickname = nicknameOptional.get();
            model.addAttribute("liked", likesService.existsLikerandLikee(principal.getName(), nickname));
            return getProfiles(model, nickname, "other-profile");
        }
    }

    private String getProfiles(Model model, String nickname, String page) {
        Optional<Profile> profileOptional = profileService.findProfileByNickname(nickname);
        if (profileOptional.isPresent()) {
            logger.info("Found " + profileOptional.get());
            ProfileFormData profileFormData = profileOptional.map(profileMapper::mapProfileToProfileFormData).get();
            logger.debug("Converted " + profileOptional.get() + " to " + profileFormData);
            model.addAttribute("profile", profileFormData);
            logger.info("Returned profile of " + profileFormData);
            return page;
        } else {
            logger.info("Did not found any Profile for " + nickname);
            return "redirect:/home";
        }
    }

    @GetMapping("profiles/search")
    public String search(Model model, @ModelAttribute SearchFormData searchData) {
        logger.debug("Received " + searchData);
        SearchFilter filter = searchMapper.mapSearchFormDataToSearchFilter(searchData);
        List<ProfileFormData> profileFormDataList = profileService
                .search(filter)
                .stream()
                .map(profileMapper::mapProfileToProfileFormData)
                .toList();
        logger.info("Found " + profileFormDataList);
        model.addAttribute("profiles", profileFormDataList);
        model.addAttribute("searchData", searchData);
        return "search";
    }

    @PostMapping("profiles/save")
    public String saveProfile(
            @ModelAttribute Optional<ProfileFormData> profileFormDataOptional,
            @RequestParam(value = "image") MultipartFile file
    ) {
        logger.debug("Received " + profileFormDataOptional.toString() + "\n" + file);

        Optional<Photo> photoOptional = Optional.empty();

        if (!file.isEmpty()) {
            try {
                photoOptional = photoService.upload(file.getBytes(), true);
            } catch (IOException e) {
                logger.warn("Could not get byte array from MultipartFile  \n"
                        + Arrays.toString(e.getStackTrace()));
            }
        }

        if (profileFormDataOptional.isPresent()) {
            Profile profile = profileMapper.mapProfileFormDataToProfile(profileFormDataOptional.get());
            profileService.save(profile, photoOptional);
            logger.info("Saved " + profile);
        } else {
            logger.warn("Could not save data of " + profileFormDataOptional);
        }

        return "redirect:/profiles";
    }
}
