package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {

    private PassportRepository passportRepository;

    public PassportService(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    public boolean create(Passport passport) {
        boolean rsl = false;
     if (this.passportRepository.findBySeriesAndNumber(passport.getSeries(),
             passport.getNumber()).isEmpty()) {
         this.passportRepository.save(passport);
         rsl = true;
     }
     return rsl;
    }

    public boolean delete(int id) {
        boolean rsl = false;
        if (this.passportRepository.deletePassport(id) > 0) {
            rsl = true;
        }
        return rsl;
    }

    public List<Passport> findAll() {
        return (List<Passport>) this.passportRepository.findAll();
    }

    public Boolean update(int id, Passport passport) {
        Optional passportOpt = passportRepository.findById(id);
        Boolean rsl = null;
        if (passportOpt.isPresent()) {
            passport.setId(id);
            this.passportRepository.save(passport);
            rsl = true;
        } else {
            rsl =  false;
        }
        return rsl;
    }

    public List<Passport> findSeries(int series) {
        return this.passportRepository.findBySeries(series);
    }

    public List<Passport> findExpired() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -20);
        Date expired = calendar.getTime();
        return this.passportRepository.findExpired(expired);
    }

    public List<Passport> findReplaced() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -20);
        Date expired = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date replacing = calendar.getTime();
        return this.passportRepository.findReplaced(expired, replacing);
    }
}
