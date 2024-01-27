package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.response.UsersListResponseDTO;
import it.soriani.tefo.entity.Users;
import it.soriani.tefo.mapper.UsersMapper;
import it.soriani.tefo.service.UsersService;
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
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GenericConstants.API_BASE_PATH + UsersController.API_CONTEXT)
@Slf4j
public class UsersController {

    protected static final String API_CONTEXT = "/users";

    private final UsersService usersService;
    private final UsersMapper usersMapper;

    @GetMapping("/allUsers")
    @Operation(
            summary = "Retrieve all users from database",
            description = "Retrieve all result from table users in database",
            method = "GET",
            tags = "users",
            operationId = "allUsers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(
                                            implementation = UsersListResponseDTO.class
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
    public ResponseEntity<UsersListResponseDTO> transferUsers() {
        final List<Users> usersList = usersService.getAllUsers();
        return ResponseEntity.ok()
                .body(UsersListResponseDTO.builder()
                        .payload(usersMapper.entityListToDtoList(usersList))
                        .build()
                );
    }

}
