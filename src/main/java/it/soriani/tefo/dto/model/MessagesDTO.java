package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessagesDTO {

    private Integer mid;

    private Integer uid;

    private Integer readState;

    private Integer sendState;

    private Integer date;

    private byte[] data;

    private Integer out;

    private MessagesManiputaled messagesManiputaled;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessagesManiputaled {

        private String content;
        private UsersDTO.UsersManipulated fromUser;
        private UsersDTO.UsersManipulated toUser;
        private Boolean isRead;
        private Boolean isSent;
        private Boolean pinned;
        private String date;
        private Integer countForward;
        private Boolean hasMedia;
        private String mimeType;
        private String media;
    }

}
