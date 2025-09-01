package week3.enums;

public enum AppointmentStatus {
    SCHEDULED,
    CHECKED_IN,
    RESCHEDULED,
    COMPLETED,
    CANCELLED,
    NO_SHOW;

    public boolean canTransitionTo(AppointmentStatus newStatus) {
        return switch (this) {
            case SCHEDULED ->
                            newStatus == CHECKED_IN
                            || newStatus == CANCELLED
                            || newStatus == NO_SHOW
                            || newStatus == RESCHEDULED; // ação de reagendar
            case CHECKED_IN ->
                            newStatus == COMPLETED
                            || newStatus == CANCELLED;   // pode cancelar após check-in
            case RESCHEDULED ->
                    newStatus == CHECKED_IN
                            || newStatus == CANCELLED
                            || newStatus == NO_SHOW; // se o reagendamento foi abortado
            case COMPLETED, CANCELLED, NO_SHOW -> false; // finais
        };
    }
}