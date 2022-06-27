package toyproject.courseenrollment.domain.enumtype;

public enum CompletionType {
    //전선, 전필
    MajorRequirements("전필");

    private final String description;

    CompletionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
