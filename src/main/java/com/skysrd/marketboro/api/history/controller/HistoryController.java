package com.skysrd.marketboro.api.history.controller;

import com.skysrd.marketboro.api.history.domain.dto.HistorySearchRequest;
import com.skysrd.marketboro.api.history.service.HistoryService;
import com.skysrd.marketboro.common.result.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("")
    public ResponseEntity<?> getHistory(HistorySearchRequest historySearchRequest) {
        return ResponseHandler.generate()
                .status(HttpStatus.OK)
                .data(historyService.getHistory(historySearchRequest))
                .build();
    }
}
