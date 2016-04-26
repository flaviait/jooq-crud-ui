package de.flaviait.crud.backoffice.repositories;

import de.flaviait.crud.backoffice.models.IdentifiableDTO;
import org.jooq.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCRUDRepository<R extends UpdatableRecord, ID, P extends IdentifiableDTO<ID>> {

  protected final DSLContext dslContext;
  private final Table<R> table;
  private final Field<ID> idField;
  private final Class<P> pojoClass;

  public AbstractCRUDRepository(DSLContext dslContext, Table<R> table, Field<ID> idField, Class<P> pojoClass) {
    this.dslContext = dslContext;
    this.table = table;
    this.pojoClass = pojoClass;
    this.idField = idField;
  }

  public List<P> getPage(Integer page, Integer pageSize, String sortField, String sortOrder, Map<String, Object> filterQuery) {
    String sortFieldName = sortField.replaceAll("([A-Z])", "_$1").toLowerCase();
    return dslContext.selectFrom(table)
      .where(filter(filterQuery != null ? filterQuery : new HashMap<>()))
      .orderBy(table.field(sortFieldName).sort(SortOrder.valueOf(sortOrder)))
      .limit(pageSize)
      .offset((page - 1) * pageSize)
      .fetchInto(pojoClass);
  }

  public P getById(ID id) {
    return getRecordById(id).into(pojoClass);
  }

  public P update(ID id, P pojo) {
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

  public void delete(ID id) {
    dslContext.delete(table).where(idField.eq(id)).execute();
  }

  protected Condition filter(Map<String, Object> filterQuery) {
    return null;
  }

  protected R getRecordById(ID id) {
    return dslContext.selectFrom(table)
      .where(idField.eq(id))
      .fetchOne();
  }

  protected R newRecord(Object obj) {
    return dslContext.newRecord(table, obj);
  }
}
