package de.flaviait.crud.backoffice.repositories;

import org.jooq.*;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractCRUDRepository<R extends UpdatableRecord, P extends Serializable> {

  protected final DSLContext dslContext;
  private final Table<R> table;
  private final Field<Long> idField;
  private final Class<P> pojoClass;

  public AbstractCRUDRepository(DSLContext dslContext, Table<R> table, Class<P> pojoClass) {
    this.dslContext = dslContext;
    this.table = table;
    this.pojoClass = pojoClass;
    this.idField = table.field("id", Long.class);
  }

  public List<P> getPage(Integer page, Integer pageSize, String sortField, String sortOrder) {
    String sortFieldName = sortField.replaceAll("([A-Z])", "_$1").toLowerCase();
    return dslContext.selectFrom(table)
      .orderBy(table.field(sortFieldName).sort(SortOrder.valueOf(sortOrder)))
      .limit(pageSize)
      .offset((page - 1) * pageSize)
      .fetchInto(pojoClass);
  }

  public P getById(Long id) {
    return getRecordById(id).into(pojoClass);
  }

  public P update(Long id, P pojo) {
    R record = getRecordById(id);
    record.from(pojo);
    record.store();
    return record.into(pojoClass);
  }

  public P create(P pojo) {
    R record = newRecord(pojo);
    record.store();
    return record.into(pojoClass);
  }

  public void delete(Long id) {
    dslContext.delete(table).where(idField.eq(id)).execute();
  }

  protected R getRecordById(Long id) {
    return dslContext.selectFrom(table)
      .where(idField.eq(id))
      .fetchOne();
  }

  protected R newRecord(Object obj) {
    return dslContext.newRecord(table, obj);
  }
}
