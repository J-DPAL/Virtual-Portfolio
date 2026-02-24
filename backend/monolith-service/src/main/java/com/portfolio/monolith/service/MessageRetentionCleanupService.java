package com.portfolio.monolith.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageRetentionCleanupService {

  private static final Logger log = LoggerFactory.getLogger(MessageRetentionCleanupService.class);

  private final MessageDataService messageDataService;

  @Value("${app.messages.retention.enabled:true}")
  private boolean retentionEnabled;

  @Value("${app.messages.retention.read-days:14}")
  private int retentionReadDays;

  public MessageRetentionCleanupService(MessageDataService messageDataService) {
    this.messageDataService = messageDataService;
  }

  @Scheduled(cron = "${app.messages.retention.cron:0 0 3 * * *}")
  public void cleanupReadMessages() {
    if (!retentionEnabled) {
      return;
    }
    int deleted = messageDataService.deleteReadMessagesOlderThanDays(retentionReadDays);
    if (deleted > 0) {
      log.info(
          "Message retention cleanup deleted {} read messages older than {} days",
          deleted,
          retentionReadDays);
    } else {
      log.debug("Message retention cleanup found nothing to delete");
    }
  }
}

