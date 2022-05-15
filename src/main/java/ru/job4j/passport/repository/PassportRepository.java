package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.passport.domain.Passport;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    @Query("from Passport p where p.created <= :expiredDate")
    public List<Passport> findExpired(@Param("expiredDate") Date expiredDate);

    @Query("from Passport p where p.created between :expiredDate and :replacingDate")
    public List<Passport> findReplaced(@Param("expiredDate") Date expiredDate,
                                       @Param("replacingDate") Date replacingDate);

    public List<Passport> findBySeries(int series);

    @Transactional
    @Modifying
    @Query("delete Passport p where p.id = ?1")
    public int deletePassport(int id);

    @Query(value = "from Passport p where p.series = ?1 and p.number = ?2")
    public Optional<Passport> findBySeriesAndNumber(int series, int number);
}
