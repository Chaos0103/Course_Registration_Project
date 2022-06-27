package toyproject.courseenrollment.domain.enumtype;

public enum Language {
    KOREAN("한국어"), ENGLISH("영어");

    private final String description;

    Language(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
