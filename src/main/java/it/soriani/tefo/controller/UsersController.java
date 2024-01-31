package it.soriani.tefo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.dto.pagination.PageDTO;
import it.soriani.tefo.dto.response.UsersPaginatiedResponseDTO;
import it.soriani.tefo.service.UsersService;
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

    @PostMapping("/allUsers")
    @Operation(
            summary = "Retrieve all users from database",
            description = "Retrieve all result from table users in database",
            method = "POST",
            tags = "users",
            operationId = "allUsers",
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
                                    schema = @Schema(
                                            implementation = UsersPaginatiedResponseDTO.class
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
    public ResponseEntity<UsersPaginatiedResponseDTO> getAllUsers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            Pageable pageable,
            @RequestBody UsersDTO.UsersManipulated usersManipulated) {
        pageable = PaginationUtility.checkPagination(page, size, sort, pageable);
        final Page<UsersDTO> users = usersService.getAllUsers(pageable, usersManipulated);
        final PageDTO pageUsers = PaginationUtility.generatePagination(users);
        return ResponseEntity.ok()
                .body(UsersPaginatiedResponseDTO.builder()
                        .payload(users.getContent())
                        .page(pageUsers)
                        .build()
                );
    }

}
