package app.models.enums;

public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public String toRussianName() {
        switch (this) {
            case MONDAY -> {
                return "Понедельник";
            }
            case TUESDAY -> {
                return "Вторник";
            }
            case WEDNESDAY -> {
                return "Среда";
            }
            case THURSDAY -> {
                return "Четверг";
            }
            case FRIDAY -> {
                return "Пятница";
            }
            case SATURDAY -> {
                return "Суббота";
            }
            case SUNDAY -> {
                return "Воскресенье";
            }
        }
        return null;
    }
}
