package it.soriani.tefo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@Entity
@Table(name = "contacts")
public class Contacts implements Serializable {

    @Id
    @Column(name = "uid", updatable = false, nullable = false)
    private int uid;

    @Column(name = "mutual", nullable = false)
    private boolean mutual;

}
