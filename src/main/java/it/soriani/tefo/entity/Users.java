package it.soriani.tefo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Data
public class Users implements Serializable {

    @Id
    @Column(name = "uid", updatable = false, nullable = false)
    private int uid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "data", nullable = false)
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private Contacts contact;

}
