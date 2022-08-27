package smswh.domain.activity;

import lombok.Getter;
import smswh.domain.Member;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

// 여행, 밥약, 취미 생활, 기타
@Entity
@Getter
public class Activity {

    @Id @GeneratedValue
    private Long Id;

    private String title;

    @ManyToOne
    @Column(name = "uploader_id")
    private Member uploader;

    @OneToMany
    private List<Member> applyMembers;

    @Min(2)
    private int maxAppliers;

    private LocalDateTime uploadDateTime;

    @NotNull
    private Calendar plannedDateTime;

    private String location;

    @NotNull
    private String explanation;

    private ActivityStatus status;

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

}
