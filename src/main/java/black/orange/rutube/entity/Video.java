package black.orange.rutube.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Instant createdDate = Instant.now();
    private String link;
    @Enumerated(EnumType.STRING)
    private VideoStatus videoStatus = VideoStatus.REVIEW;
}
