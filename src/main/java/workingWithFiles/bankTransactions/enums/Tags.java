package workingWithFiles.bankTransactions.enums;

public enum Tags {

    TAG_NAME ("name"),
    TAG_ID ("id"),
    TAG_SENDER ("sender_number"),
    TAG_RECIPIENT ("recipient_number"),
    TAG_AMOUNT ("amount"),
    TAG_CURRENCY ("currency"),
    TAG_DATA ("dataTime"),
    TAG_PURPOSE ("purpose"),
    TAG_STATUS ("status"),
    TAG_ELEMENT ("element"),
    TAG_TRANSFERS ("transfers");
    private final String title;

    Tags(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    }
