package org.courseworks.ris.cmanager.session;

import java.lang.reflect.Field;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.DatabaseLowlevelProcessor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DbTable extends EntitySet {

	private EntitySet _generalTable;
	private ExtendedSession _session;

	public DbTable(Class<?> type, ExtendedSession session,
			EntitySet generalTable) {
		super(type);
		_session = session;
		_generalTable = generalTable;
	}

	// ------------------------- Synch methods -----------------------------

	public void fillTable(ExtendedSession session) {
		clear();
		for (AbstractEntity entity : DatabaseLowlevelProcessor.selectFromTable(
				session, _type)) {
			entity.setTable(this);
			addItem(entity);

			_generalTable.addItem(entity);
		}
	}

	public void addNewItem(AbstractEntity item) {
		Session hbSession = _session.getHBSession();
		Transaction tx = hbSession.beginTransaction();
		hbSession.save(item);
		tx.commit();

		addItem(item);
		_generalTable.addItem(item);
	}

	public void updateItem(AbstractEntity item) {
		Session hbSession = _session.getHBSession();
		Transaction tx = hbSession.beginTransaction();
		hbSession.update(item);
		tx.commit();
	}

	public void deleteItem(AbstractEntity item) {
		Session hbSession = _session.getHBSession();
		Transaction tx = hbSession.beginTransaction();
		hbSession.delete(item);
		tx.commit();

		removeItem(item);
		_generalTable.removeItem(item);
	}

	// ------------------------- Rel methods -----------------------------

	public DbTable getRelatedTable(Field field) {
		Class<?> type = field.getType();
		for (DbTable table : _session.getTables()) {
			if (table.getContentType().equals(type)) {
				return table;
			}
		}
		return null;
	}

	public ExtendedSession getSession() {
		return _session;
	}

}
