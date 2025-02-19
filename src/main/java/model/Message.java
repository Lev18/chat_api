package main.java.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table (indexes = {
    @Index(name = "m_sndr_ind", columnList = "sender"),
    @Index(name = "m_rsvr_ind", columnList = "receiver")
    })
public class Message extends PanacheEntity {
    // indexing for faster queries
    public String sender;
    
    public String receiver;

    public String content;
    public LocalDateTime time = LocalDateTime.now();

 }
