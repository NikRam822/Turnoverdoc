package com.turnoverdoc.turnover.model;

public enum OrderStatus {
    // Now this status with current name is required for search created orders in database
    RECEIVED, //TODO: Change status name to CONTACT_RECEIVED
    REQUEST_FOR_DOCS,
    ALL_DOCS_RECEIVED,
    REFUND_REQUEST_SENT_HMRC,
    TAX_RETURN_TO_HMRC,
    CHECK_RECEIVED,
    CHECK_BANKED,
    REQUEST_FOR_BANK_DETAILS,
    BANK_DETAILS_RECEIVED,
    TRANSFER_MADE
}
