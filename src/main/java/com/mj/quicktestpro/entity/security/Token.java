package com.mj.quicktestpro.entity.security;
import com.mj.quicktestpro.entity.userType.UserType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "token instance")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserType userType;
}
