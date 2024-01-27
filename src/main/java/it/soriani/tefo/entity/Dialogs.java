package it.soriani.tefo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "dialogs")
@Data
public class Dialogs implements Serializable {

    @Id
    @Column(name = "did", updatable = false, nullable = false)
    private Integer did;

    @Column(name = "date", nullable = false)
    private Integer date;

    @Column(name = "unread_count", nullable = false)
    private Integer unreadCount;

    @Column(name = "inbox_max", nullable = false)
    private Integer inboxMax;

    @Column(name = "outbox_max", nullable = false)
    private Integer outboxMax;

    @Column(name = "last_mid", nullable = false)
    private Integer lastMessageId;

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

}
