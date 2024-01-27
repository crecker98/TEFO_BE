package it.soriani.tefo.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DialogsDTO {

    private Integer did;

    private Integer date;

    private Integer unreadCount;

    private Integer inboxMax;

    private Integer outboxMax;

    private Integer lastMessageId;

    private Boolean pinned;

}
