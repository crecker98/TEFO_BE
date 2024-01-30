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
 * @author christiansoriani on 29/01/24
 * @project TEFO_BE
 */

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chats")
public class Chats implements Serializable {

    @Id
    @Column(name = "uid", updatable = false, nullable = false)
    private Integer uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "data", nullable = false)
    private byte[] data;

}
