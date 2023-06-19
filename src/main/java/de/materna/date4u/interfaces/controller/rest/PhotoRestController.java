package de.materna.date4u.interfaces.controller.rest;

import de.materna.date4u.core.entity.Photo;
import de.materna.date4u.core.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Tag(name = "Photo", description = "Photo Rest Controller")
@SecurityRequirement(name = "basicScheme")
public class PhotoRestController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoRestController.class);

    private final PhotoService photoService;

    public PhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Operation(
            summary = "Get Photo by id",
            description = "This endpoint produces a byte array containing the photo, required by the id",
            tags = {"Photo"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(description = "successful operation",
                            responseCode = "200",
                            content = @Content(mediaType = "image/jpeg")),
                    @ApiResponse(description = "failure in finding photo",
                            responseCode = "400",
                            content = @Content)}
    )
    @GetMapping(path = "photos/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPhoto(
            @Parameter(description = "id of the photo", required = true, schema = @Schema(type = "integer", format = "int64"))
            @PathVariable Long id
    ) {
        logger.debug("Received " + id);
        Optional<Photo> photoOptional = photoService.findPhotoById(id);
        if (photoOptional.isPresent()) {
            Optional<byte[]> bytesOptional = photoService.download(photoOptional.get().getName());
            if (bytesOptional.isPresent()) {
                logger.info("Found photo with id " + id);
                return ResponseEntity.of(bytesOptional);
            }
        }
        logger.warn("Could not find photo with id " + id);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
