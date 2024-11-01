package com.mj.quicktestpro.entity.userType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user instance")
public class UserType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String username;
    @Email
    private String email;
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    private LocalDate dateOfBirth;
    @OneToOne
    @JoinColumn(name = "examinerId")
    private ExaminerType examinerType;
    @OneToOne
    @JoinColumn(name = "participantId")
    private ParticipantType participantType;
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roleEnums = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
}
