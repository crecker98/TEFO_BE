package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 02/02/24
 * @project TEFO_BE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatsDTO {

    private Integer uid;

    private String name;

    private byte[] data;

    private ChatsManipulated chatsManipulated;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatsManipulated {
        private String title;
        private Integer numberOfParticipants;
        private Boolean left;
        private String creationDate;
        private BannedRightManipulated bannedRights;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BannedRightManipulated {
        private boolean canViewMessages;
        private boolean canSendMessages;
        private boolean canSendMedia;
        private boolean canChangeInfo;
        private boolean canInviteUsers;
    }


}
