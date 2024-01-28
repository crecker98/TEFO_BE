package it.soriani.tefo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages_v2")
@Data
public class Messages {

    @Id
    @Column(name = "mid", updatable = false, nullable = false)
    private Integer mid;

    @Column(name = "read_state", nullable = false)
    private Integer readState;

    @Column(name = "send_state", nullable = false)
    private Integer sendState;

    @Column(name = "date", nullable = false)
    private Integer date;

    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "out", nullable = false)
    private Integer out;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Users users;

}
