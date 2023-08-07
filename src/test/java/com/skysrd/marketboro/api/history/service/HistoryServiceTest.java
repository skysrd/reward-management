package com.skysrd.marketboro.api.history.service;

import com.skysrd.marketboro.api.history.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    private HistoryService historyService;

    @Mock
    private HistoryRepository historyRepository;

    @Test
    void getSaveHistory() {
    }

    @Test
    void getSpendHistory() {
    }
}