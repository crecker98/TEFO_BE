package it.soriani.tefo.controller;

import io.netty.buffer.Unpooled;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.response.MessagesListResponseDTO;
import it.soriani.tefo.entity.Messages;
import it.soriani.tefo.mapper.MessagesMapper;
import it.soriani.tefo.service.MessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import telegram4j.tl.TlDeserializer;

import java.util.List;

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
    private final MessagesMapper messagesMapper;

    @GetMapping("/allMessages")
    @Operation(
            summary = "Retrieve all messages from database",
            description = "Retrieve all result from table messages in database",
            method = "GET",
            tags = "messages",
            operationId = "allMessages",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = MessagesListResponseDTO.class
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
    public ResponseEntity<MessagesListResponseDTO> getAllMessages() {
        final List<Messages> messages = messagesService.getAllMessages();
        TlDeserializer.deserialize(Unpooled.copiedBuffer(messages.get(2).getData()));
        return ResponseEntity.ok(
                MessagesListResponseDTO.builder()
                        .payload(messagesMapper.entityListToDtoList(messages))
                        .build()
        );
    }

}
