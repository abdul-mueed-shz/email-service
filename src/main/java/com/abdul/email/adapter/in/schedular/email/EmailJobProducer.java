package com.abdul.email.adapter.in.schedular.email;

import com.abdul.email.adapter.in.schedular.Producer;
import com.abdul.email.domain.email.enums.EmailStatusEnum;
import com.abdul.email.domain.email.port.in.FetchEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EmailJobProducer implements Producer {
    
    private final FetchEmailUseCase fetchEmailUseCase;
    private final Set<Long> processedIds = ConcurrentHashMap.newKeySet();


    @Override
    public List<Long> produce() {
        List<Long> unprocessedIds = fetchEmailUseCase.findByStatusAndSufficientRetries(EmailStatusEnum.PENDING);
        List<Long> newIds = unprocessedIds.stream().filter(id -> !processedIds.contains(id)).toList();
        processedIds.addAll(newIds);
        return newIds;
    }
}
