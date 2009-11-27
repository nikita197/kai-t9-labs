package org.courseworks.ris.mappings.hprepair;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.main.AbstractEntity;

@Entity
@Table(name="Cars")
public class Cars extends AbstractEntity{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	public String Name;
}
