package com.abdul.email.adapter.out.persistence.entity;

import com.abdul.email.domain.email.enums.EmailStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "email")
@AllArgsConstructor
@NoArgsConstructor
public class Email extends BaseEntity {

    @Column(nullable = false)
    private String targetEmail;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String body;

    @Column
    @Enumerated(value = EnumType.ORDINAL)
    private EmailStatusEnum status = EmailStatusEnum.PENDING;

    @Column
    @Max(value = 3, message = "Retry attempt cannot exceed 5")
    private Long retryAttempt = 0L;
}
