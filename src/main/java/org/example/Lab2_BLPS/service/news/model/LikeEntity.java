package org.example.Lab2_BLPS.service.news.model;

import lombok.*;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "like_ref")
public class LikeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne(optional = false)
    @JoinColumn(name = "news_id")
    private NewsEntity news;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_of_model")
    private StateOfModel stateOfModel;

    public String toString() {
        return "LikeEntity(id=" + id + "; username=" + username + "; newsId=" + news.getId() + ")";
    }
}
