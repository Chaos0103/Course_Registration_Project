package toyproject.courseenrollment.domain.enumtype;

public enum Major {
    COMPUTER("컴퓨터공학과"), SOFTWARE("소프트웨어학과");

    private final String description;

    Major(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
