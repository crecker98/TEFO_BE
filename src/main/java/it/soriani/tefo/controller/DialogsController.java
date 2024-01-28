package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.response.DialogsListResponseDTO;
import it.soriani.tefo.entity.Dialogs;
import it.soriani.tefo.mapper.DialogsMapper;
import it.soriani.tefo.service.DialogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + DialogsController.API_CONTEXT)
@Slf4j
public class DialogsController {

    protected static final String API_CONTEXT = "/dialogs";

    private final DialogsService dialogsService;
    private final DialogsMapper dialogsMapper;

    @GetMapping("/allDialogs")
    @Operation(
            summary = "Retrieve all dialogs from database",
            description = "Retrieve all result from table dialogs in database",
            method = "GET",
            tags = "dialogs",
            operationId = "allDialogs",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = DialogsListResponseDTO.class
                                    )
                            ),
                            description = "List retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request: one of the specified parameters is not valid"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service unavailable"
                    )
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DialogsListResponseDTO> getAllDialogs() {
        final List<Dialogs> dialogs = dialogsService.getAllDialogs();
        return ResponseEntity.ok(
                DialogsListResponseDTO.builder()
                        .payload(dialogsMapper.entityListToDtoList(dialogs))
                        .build()
        );
    }

}
