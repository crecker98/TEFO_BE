package it.soriani.tefo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Entity
@Table(name = "users")
@Data
public class Users implements Serializable {

    @Id
    @Column(name = "uid", updatable = false, nullable = false)
    private int uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private LocalDateTime status;

    @Column(name = "data", nullable = false)
    private byte[] data;

}
