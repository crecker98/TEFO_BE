package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 25/01/24
 * @project TEFO_BE
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    private long uid;

    private String name;

    private Integer status;

    private byte[] data;

    private ContactsDTO contact;

    private UsersManipulated usersManipulated;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsersManipulated {

        private String username;

        private String nameAndSurname;

        private String lastStatus;

        private String phoneNumber;

        private Boolean isInContactsList;

    }

}
