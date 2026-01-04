package com.abdul.email.adapter.in.schedular.email;

import com.abdul.email.adapter.in.schedular.Consumer;
import com.abdul.email.domain.email.port.in.ProcessEmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailJobConsumer implements Consumer {
    private final ProcessEmailUseCase processEmailUseCase;

    @Override
    @Async("threadPoolTaskExecutor")
    public synchronized void consume(Long id) {
        processEmailUseCase.execute(id);
    }
}
