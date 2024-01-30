package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.dto.response.UsersListResponseDTO;
import it.soriani.tefo.mapper.UsersMapper;
import it.soriani.tefo.service.UsersService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @PostMapping("/allUsers")
    @Operation(
            summary = "Retrieve all users from database",
            description = "Retrieve all result from table users in database",
            method = "POST",
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
    public ResponseEntity<UsersListResponseDTO> getAllUsers() {
        final List<UsersDTO> usersList = usersService.getAllUsers();
        return ResponseEntity.ok()
                .body(UsersListResponseDTO.builder()
                        .payload(usersList)
                        .build()
                );
    }

    @PostMapping("/allUsersFromContacts")
    @Operation(
            summary = "Retrieve all users in the contacts list from database",
            description = "Retrieve all result from table users filtered with mutual = 1 in contacts in database",
            method = "POST",
            tags = "users",
            operationId = "allUsersFromContacts",
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
    public ResponseEntity<UsersListResponseDTO> getAllUserInContacts() {
        final List<UsersDTO> usersList = usersService.getAllUserFromContacts();
        return ResponseEntity.ok()
                .body(UsersListResponseDTO.builder()
                        .payload(usersList)
                        .build()
                );
    }

    @PostMapping("/allUsersFiltered")
    @Operation(
            summary = "Retrieve all users filtered based on input from database",
            description = "Retrieve all result from table users filtered based on input in database",
            method = "POST",
            tags = "users",
            operationId = "allUsersFiltered",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                    implementation = UsersDTO.UsersManipulated.class
                            )
                    )
            ),
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
    public ResponseEntity<UsersListResponseDTO> getAllUserFiltered(@NotNull @NotEmpty @RequestBody UsersDTO.UsersManipulated usersManipulated) {
        final List<UsersDTO> usersList = usersService.getAllUserFiltered(usersManipulated);
        return ResponseEntity.ok()
                .body(UsersListResponseDTO.builder()
                        .payload(usersList)
                        .build()
                );
    }

}
