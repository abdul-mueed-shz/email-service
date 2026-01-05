package com.abdul.email.adapter.out.persistence.entity;

import com.abdul.email.domain.email.enums.EmailStatusEnum;
import com.abdul.email.domain.email.enums.EmailTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "email")
@AllArgsConstructor
@NoArgsConstructor
public class Email extends BaseEntity {

    @Column(nullable = false)
    private String targetEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "email_variables",
            joinColumns = @JoinColumn(name = "email_id")
    )
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value", length = 2000)
    private Map<String, String> variables = new HashMap<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EmailTypeEnum type = EmailTypeEnum.TEXT;

    @Column
    @Enumerated(value = EnumType.STRING)
    private EmailStatusEnum status = EmailStatusEnum.PENDING;

    @Column
    @Max(value = 3, message = "Retry attempt cannot exceed 3")
    private Long retryAttempt = 0L;
}
