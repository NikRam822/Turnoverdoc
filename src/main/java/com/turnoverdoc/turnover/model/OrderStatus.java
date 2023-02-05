package com.turnoverdoc.turnover.model;

public enum OrderStatus {
    CONTACT_RECEIVED("Ваша заявка успешно создана. В скором времени с вами свяжется администратор для уточнения данных."),
    REQUEST_FOR_DOCS("Статус вашей заявки был обновлен. Перейдите на сайт для загрузки необходимых документов."),
    ALL_DOCS_RECEIVED("Ваши документы были успешно доставлены и подтверждены. Ожидайте подтверждения от администратора."),
    TAX_RETURN_TO_HMRC("Ваши документы были успешно подтверждены и направлены в налоговую службу."),
    CHEQUE_RECEIVED("Ваш чек был только что получен."),
    CHEQUE_BANKED("Ваш чек был успешно обналичен. Перейдите на сайт для указания ваших банковских данных."),
    BANK_DETAILS_RECEIVED("Ваши банковские данные были успешно доставлены. Ожидайте поступление денежных средств."),
    TRANSFER_MADE("Перевод денежных средств по вашим банковским данным был успешно проведен.");

    OrderStatus(String mailDescription) {
        this.mailDescription = mailDescription;
    }

    private String mailDescription;

    public String getMailDescription() {
        return mailDescription;
    }
}
