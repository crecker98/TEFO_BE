package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.common.PageDTO;
import it.soriani.tefo.dto.model.DialogsDTO;
import it.soriani.tefo.dto.response.DialogsPaginatedResponseDTO;
import it.soriani.tefo.service.DialogsService;
import it.soriani.tefo.utility.PaginationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/allDialogs")
    @Operation(
            summary = "Retrieve all dialogs from database",
            description = "Retrieve all result from table dialogs in database",
            method = "POST",
            tags = "dialogs",
            operationId = "allDialogs",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number",
                            example = "0",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "size",
                            description = "Size of the page",
                            example = "0",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Sorting criteria in the format: property(,asc|desc). " +
                                    "Default sort order is ascending. " +
                                    "Multiple sort criteria are supported.",
                            example = "id,desc",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = DialogsPaginatedResponseDTO.class
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
    public ResponseEntity<DialogsPaginatedResponseDTO> getAllDialogs(Pageable pageable) {
        pageable = PaginationUtility.checkPagination(pageable.getPageNumber(), pageable);
        final Page<DialogsDTO> dialogs = dialogsService.getAllDialogs(pageable);
        final PageDTO pageDialogs = PaginationUtility.generatePagination(dialogs);
        return ResponseEntity.ok()
                .body(DialogsPaginatedResponseDTO.builder()
                        .payload(dialogs.getContent())
                        .page(pageDialogs)
                        .build()
                );
    }

}
