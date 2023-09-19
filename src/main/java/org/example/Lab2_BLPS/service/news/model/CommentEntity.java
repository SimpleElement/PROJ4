package org.example.Lab2_BLPS.service.news.model;

import lombok.*;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "comment_ref")
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne(optional = false)
    @JoinColumn(name = "news_id")
    private NewsEntity news;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_of_model")
    private StateOfModel stateOfModel;

    @Override
    public String toString() {
        return "CommentEntity(id=" + id + "; username=" + username + "; content=" + content + ")";
    }
}
