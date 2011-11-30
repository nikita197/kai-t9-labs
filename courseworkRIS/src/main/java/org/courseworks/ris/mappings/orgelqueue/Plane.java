package org.courseworks.ris.mappings.orgelqueue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "plane")
public class Plane extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(length = 30)
	public String name;

	public boolean state;

	@ManyToOne
	@JoinColumn(name = "engine")
	public Engine engine;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFieldPresent(String fieldName) {
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("name".equals(fieldName)) {
			return "Имя";
		} else if ("state".equals(fieldName)) {
			return "Состояние";
		} else if ("engine".equals(fieldName)) {
			return "Двигатель";
		}
		return null;
	}

}
