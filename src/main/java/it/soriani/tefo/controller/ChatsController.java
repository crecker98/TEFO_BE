package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.common.PageDTO;
import it.soriani.tefo.dto.model.ChatsDTO;
import it.soriani.tefo.dto.request.ChatsRequestDTO;
import it.soriani.tefo.dto.response.ChatsPaginatedResponseDTO;
import it.soriani.tefo.service.ChatsService;
import it.soriani.tefo.utility.PaginationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author christiansoriani on 02/02/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + ChatsController.API_CONTEXT)
@Slf4j
public class ChatsController {

    protected static final String API_CONTEXT = "/chats";

    private final ChatsService chatsService;

    @PostMapping("/allChats")
    @Operation(
            summary = "Retrieve all chats from database",
            description = "Retrieve all result from table chats in database",
            method = "POST",
            tags = "chats",
            operationId = "allChats",
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ChatsRequestDTO.class
                            )
                    ),
                    description = "Chats parameters"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ChatsPaginatedResponseDTO.class
                                    )
                            ),
                            description = "List retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found: no users found in database"
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
    public ResponseEntity<ChatsPaginatedResponseDTO> getAllChats(Pageable pageable, @RequestBody(required = false) ChatsRequestDTO request) {
        pageable = PaginationUtility.checkPagination(pageable.getPageNumber(), pageable);
        final Page<ChatsDTO> chats = chatsService.getAllChats(pageable, request);
        final PageDTO pageChats = PaginationUtility.generatePagination(chats);
        return ResponseEntity.ok()
                .body(ChatsPaginatedResponseDTO.builder()
                        .payload(chats.getContent())
                        .page(pageChats)
                        .build()
                );
    }

}
