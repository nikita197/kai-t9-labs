package org.courseworks.ris.mappings.orgelqueue;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "engine")
public class Engine extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	public String mark;

	@Temporal(value = TemporalType.DATE)
	public Date makedate;

	@Override
	public String getName() {
		return mark;
	}

}
