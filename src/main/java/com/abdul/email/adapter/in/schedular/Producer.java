package com.abdul.email.adapter.in.schedular;

import jakarta.transaction.Transactional;

import java.util.List;

public interface Producer {
    @Transactional
    List<Long> produce();
}
