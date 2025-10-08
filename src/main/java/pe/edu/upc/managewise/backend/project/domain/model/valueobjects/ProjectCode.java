package pe.edu.upc.managewise.backend.project.domain.model.valueobjects;

import java.util.Random;

public class ProjectCode {
    private final String value;
    public ProjectCode() {
        this.value = generateProjectCode();
    }
    private String generateProjectCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder projectCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            projectCode.append(characters.charAt(randomIndex));
        }
        return projectCode.toString();
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectCode that = (ProjectCode) o;
        return value.equals(that.value);
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    @Override
    public String toString() {
        return value;
    }
}
