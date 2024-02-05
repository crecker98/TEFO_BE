package it.soriani.tefo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author christiansoriani on 05/02/24
 * @project TEFO_BE
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enc_chats")
public class EncChats {

    @Id
    @Column(name = "uid", updatable = false, nullable = false)
    private Long uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "data", nullable = false)
    private byte[] data;


}
