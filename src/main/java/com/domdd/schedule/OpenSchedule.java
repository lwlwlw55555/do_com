package com.domdd.schedule;

import com.domdd.service.OpenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@Profile("prod")
public class OpenSchedule {
    private final OpenService openService;

    //@Scheduled(cron = "0 1 3-5/1 * * *")
    public void startRefreshOrderList() {
        log.info("startRefreshOrderList-begin");
        openService.startRefreshOrderList();
    }

    //@Scheduled(cron = "0 10 3-5/1 * * *")
    public void startRefreshAfterSaleList() {
        log.info("startRefreshAfterSaleList-begin");
        openService.startRefreshAfterSaleList();
    }

    //@Scheduled(cron = "0 20 3-5/1 * * *")
    public void startRefreshAfterSaleRefundList() {
        log.info("startRefreshAfterSaleRefundList-begin");
        openService.startRefreshAfterSaleRefundList();
    }

    //@Scheduled(cron = "0 30 3-5/1 * * *")
    public void startRefreshInventoryList() {
        log.info("startRefreshInventoryList-begin");
        openService.startRefreshInventoryList();
    }

    //@Scheduled(cron = "0 40 3-5/1 * * *")
    public void startRefreshPurchaseInOrder() {
        log.info("startRefreshPurchaseInOrder-begin");
        openService.startRefreshPurchaseInOrder();
    }
}
