package toyproject.courseenrollment.domain.enumtype;

public enum GradeType {
    AP("A+", 4.5f), AZ("A0", 4.0f), BP("B+", 3.5f), BZ("B0", 30.f),
    CP("C+", 2.5f), CZ("C0", 2.0f), DP("D+", 1.5f), DZ("D0", 1.0f),
    F("F", 0.0f), P("P", 0.0f), NP("NP", 0.0f);

    private final String description;
    private final float score;

    GradeType(String description, float score) {
        this.description = description;
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public float getScore() {
        return score;
    }
}
