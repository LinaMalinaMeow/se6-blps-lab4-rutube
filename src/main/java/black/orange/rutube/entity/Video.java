package black.orange.rutube.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "video")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Video extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(name = "video_status")
    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus = VideoStatus.REVIEW;

    @JoinColumn(name = "user_id")
    private Long userId;
}
