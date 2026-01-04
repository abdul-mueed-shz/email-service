package com.abdul.email.adapter.in.schedular.email;

import com.abdul.email.adapter.in.schedular.Consumer;
import com.abdul.email.adapter.in.schedular.GenericSchedular;
import com.abdul.email.adapter.in.schedular.Producer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailJobSchedular extends GenericSchedular {
    protected EmailJobSchedular(Producer producer, Consumer consumer) {
        super(producer, consumer);
    }

    @Scheduled(cron = "${email-job.cron}")
    public void sendTextualEmailJob() {
        this.execute();
    }
}
