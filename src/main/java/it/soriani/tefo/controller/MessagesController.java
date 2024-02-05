package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.common.PageDTO;
import it.soriani.tefo.dto.model.MessagesDTO;
import it.soriani.tefo.dto.request.MessagesRequestDTO;
import it.soriani.tefo.dto.response.MessagesPaginationResponseDTO;
import it.soriani.tefo.service.MessagesService;
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
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + MessagesController.API_CONTEXT)
@Slf4j
public class MessagesController {

    protected static final String API_CONTEXT = "/messages";

    private final MessagesService messagesService;

    @PostMapping("/allMessages")
    @Operation(
            summary = "Retrieve all messages from database",
            description = "Retrieve all result from table messages in database",
            method = "GET",
            tags = "messages",
            operationId = "allMessages",
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
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    implementation = MessagesRequestDTO.class
                            )
                    ),
                    description = "Messages request object"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = MessagesPaginationResponseDTO.class
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
    public ResponseEntity<MessagesPaginationResponseDTO> getAllMessages(Pageable pageable, @RequestBody MessagesRequestDTO messagesRequestDTO) {
        pageable = PaginationUtility.checkPagination(pageable.getPageNumber(), pageable);
        final Page<MessagesDTO> messages = messagesService.getAllMessages(pageable, messagesRequestDTO);
        final PageDTO pageMessages = PaginationUtility.generatePagination(messages);
        return ResponseEntity.ok()
                .body(MessagesPaginationResponseDTO.builder()
                        .payload(messages.getContent())
                        .page(pageMessages)
                        .build()
                );
    }

}
