package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.passport.domain.Passport;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    @Query("from Passport p where p.created <= :expiredDate")
    public List<Passport> findExpired(@Param("expiredDate") Date expiredDate);

    @Query("from Passport p where p.created between :expiredDate and :replacingDate")
    public List<Passport> findReplaced(@Param("expiredDate") Date expiredDate,
                                       @Param("replacingDate") Date replacingDate);

    public List<Passport> findBySeries(int series);
}
