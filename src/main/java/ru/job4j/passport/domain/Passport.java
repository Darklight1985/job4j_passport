package ru.job4j.passport.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import ru.job4j.passport.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@NotNull(message = "Id must be non null",
        groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
   private int id;

    @Min(value = 1000,
            message = "The series must consist of 4 digits")
    @Max(value = 9999,
            message = "The series must consist of 4 digits")
    private int series;
    @Min(value = 10000000,
            message = "The number must consist of 8 digits")
    private int number;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public static Passport of(int series, int number, Date created) {
        Passport passport = new Passport();
        passport.series = series;
        passport.number = number;
        passport.created = created;
        return  passport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return series == passport.series && number == passport.number
                && Objects.equals(created, passport.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number, created);
    }

    @Override
    public String toString() {
        return "Passport{"
                + "series=" + series
                + ", number=" + number
                + ", created=" + created
                + '}';
    }
}
