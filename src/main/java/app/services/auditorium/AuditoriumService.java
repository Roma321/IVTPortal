package app.services.auditorium;

import app.models.Auditorium;

public interface AuditoriumService {
    Auditorium addAuditorium(Auditorium auditorium);
    void deleteAuditorium(Integer id);
    Iterable<Auditorium> getAll();
}
