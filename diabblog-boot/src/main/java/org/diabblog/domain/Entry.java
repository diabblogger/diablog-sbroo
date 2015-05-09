package org.diabblog.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.diabblog.domain.util.CustomDateTimeDeserializer;
import org.diabblog.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Entry.
 */
@Entity
@Table(name = "T_ENTRY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "blood_sugar_level", precision=10, scale=2)
    private BigDecimal bloodSugarLevel;

    @Column(name = "carbs")
    private String carbs;

    @Column(name = "comments")
    private String comments;

    @Column(name = "notes")
    private String notes;

    @Column(name = "correction")
    private String correction;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created")
    private DateTime created;

    @Column(name = "fast_insulin")
    private Integer fastInsulin;

    @Min(value = 0)
    @Column(name = "slow_insulin")
    private Integer slowInsulin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(BigDecimal bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public Integer getFastInsulin() {
        return fastInsulin;
    }

    public void setFastInsulin(Integer fastInsulin) {
        this.fastInsulin = fastInsulin;
    }

    public Integer getSlowInsulin() {
        return slowInsulin;
    }

    public void setSlowInsulin(Integer slowInsulin) {
        this.slowInsulin = slowInsulin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entry entry = (Entry) o;

        if ( ! Objects.equals(id, entry.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", bloodSugarLevel='" + bloodSugarLevel + "'" +
                ", carbs='" + carbs + "'" +
                ", comments='" + comments + "'" +
                ", notes='" + notes + "'" +
                ", correction='" + correction + "'" +
                ", created='" + created + "'" +
                ", fastInsulin='" + fastInsulin + "'" +
                ", slowInsulin='" + slowInsulin + "'" +
                '}';
    }
}
