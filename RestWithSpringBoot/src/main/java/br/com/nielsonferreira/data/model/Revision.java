package br.com.nielsonferreira.data.model;

import br.com.nielsonferreira.listener.EntityRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@RevisionEntity(value = EntityRevisionListener.class)
public class Revision{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_seq")
    @SequenceGenerator(name = "revision_seq", sequenceName = "REVISION_SEQ")
    @RevisionNumber
    private Long revId;

    @RevisionTimestamp
    private Date revDate;

    @Column
    private String user;

    public Revision() {
    }

    public Revision(Long revId, Date revData, String user) {
        this.revId = revId;
        this.revDate = revData;
        this.user = user;
    }

    public Long getRevId() {
        return revId;
    }

    public void setRevId(Long revId) {
        this.revId = revId;
    }

    public Date getRevDate() {
        return revDate;
    }

    public void setRevDate(Date revDate) {
        this.revDate = revDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return Objects.equals(revId, revision.revId) && Objects.equals(revDate, revision.revDate) && Objects.equals(user, revision.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(revId, revDate, user);
    }
}
