package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.response.ContactsListResponseDTO;
import it.soriani.tefo.entity.Contacts;
import it.soriani.tefo.mapper.ContactsMapper;
import it.soriani.tefo.service.ContactsService;
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
 * @author christiansoriani on 15/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + ContactsController.API_CONTEXT)
@Slf4j
public class ContactsController {

    protected static final String API_CONTEXT = "/contacts";

    private final ContactsService contactsService;
    private final ContactsMapper contactsMapper;

    @GetMapping("/allContacts")
    @Operation(
            summary = "Return all contacts from database",
            description = "Return all result from table contacts in database",
            method = "GET",
            tags = "contacts",
            operationId = "allContacts",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = ContactsListResponseDTO.class
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
    public ResponseEntity<ContactsListResponseDTO> allContacts() {
        final List<Contacts> contactsList = contactsService.getAllContacts();
        return ResponseEntity.ok(
                ContactsListResponseDTO.builder()
                        .payload(contactsMapper.entityListToDTOList(contactsList))
                        .build()
        );
    }

}
