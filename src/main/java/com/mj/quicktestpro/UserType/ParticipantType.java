package com.mj.quicktestpro.UserType;
import com.mj.quicktestpro.exam.ExamInstance;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table()
public class ParticipantType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String participantId;
    @OneToOne
    @JoinColumn(name = "user_entity_id")
    private UserType userType;
    @ManyToMany(mappedBy = "participantType")
    private List<ExamInstance> examInstanceList;
}
