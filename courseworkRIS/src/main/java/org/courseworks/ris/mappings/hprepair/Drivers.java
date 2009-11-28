package org.courseworks.ris.mappings.hprepair;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "Drivers")
public class Drivers extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String Name;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return Name;
    }
}
