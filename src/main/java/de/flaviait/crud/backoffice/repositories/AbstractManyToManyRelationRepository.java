package de.flaviait.crud.backoffice.repositories;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.UpdatableRecord;

import java.util.List;

abstract class AbstractManyToManyRelationRepository<R extends UpdatableRecord> {

  final DSLContext dslContext;
  private final Table<R> table;

  AbstractManyToManyRelationRepository(DSLContext dslContext, Table<R> table) {
    this.dslContext = dslContext;
    this.table = table;
  }

  List<Long> getRelated(Field<Long> parentIdField, Field<Long> childIdField, Long id) {
    return dslContext
      .select(childIdField)
      .from(table)
      .where(parentIdField.eq(id))
      .fetch(childIdField);
  }

  R create(Field<Long> field1, Field<Long> field2, Long id1, Long id2) {
    R record = dslContext.newRecord(table);
    record.setValue(field1, id1);
    record.setValue(field2, id2);
    record.store();
    return record;
  }

  void delete(Field<Long> field1, Field<Long> field2, Long id1, Long id2) {
    dslContext
      .deleteFrom(table)
      .where(field1.eq(id1).and(field2.eq(id2)))
      .execute();
  }

  void deleteAll(Field<Long> field, Long id) {
    dslContext.deleteFrom(table).where(field.eq(id));
  }

  void merge(Field<Long> parentField, Field<Long> childField, Long parentId, List<Long> newChildIds) {
    if(newChildIds != null) {
      List<Long> oldChildIds = getRelated(parentField, childField, parentId);
      oldChildIds.stream()
        .filter(childId -> !newChildIds.contains(childId))
        .forEach(childId -> delete(parentField, childField, parentId, childId));
      newChildIds.stream()
        .filter(childId -> !oldChildIds.contains(childId))
        .forEach(childId -> create(parentField, childField, parentId, childId));
    }
  }

}