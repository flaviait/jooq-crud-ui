package de.flaviait.crud.backoffice.repositories;

import org.jooq.*;

import java.util.List;

abstract class AbstractManyToManyRelationRepository<R extends UpdatableRecord> {

  final DSLContext dslContext;
  private final Table<R> table;

  AbstractManyToManyRelationRepository(DSLContext dslContext, Table<R> table) {
    this.dslContext = dslContext;
    this.table = table;
  }

  <T1, T2> List<T2> getRelated(Field<T1> parentIdField, Field<T2> childIdField, T1 id) {
    return dslContext
      .select(childIdField)
      .from(table)
      .where(parentIdField.eq(id))
      .fetch(childIdField);
  }

  <T1, T2> R create(Field<T1> field1, Field<T2> field2, T1 id1, T2 id2) {
    R record = dslContext.newRecord(table);
    record.setValue(field1, id1);
    record.setValue(field2, id2);
    record.store();
    return record;
  }

  <T1, T2> void delete(Field<T1> field1, Field<T2> field2, T1 id1, T2 id2) {
    dslContext
      .deleteFrom(table)
      .where(field1.eq(id1).and(field2.eq(id2)))
      .execute();
  }

  <T>void deleteAll(Field<T> field, T id) {
    dslContext.deleteFrom(table).where(field.eq(id));
  }

  <T1, T2> void merge(Field<T1> parentField, Field<T2> childField, T1 parentId, List<T2> newChildIds) {
    if(newChildIds != null) {
      List<T2> oldChildIds = getRelated(parentField, childField, parentId);
      oldChildIds.stream()
        .filter(childId -> !newChildIds.contains(childId))
        .forEach(childId -> delete(parentField, childField, parentId, childId));
      newChildIds.stream()
        .filter(childId -> !oldChildIds.contains(childId))
        .forEach(childId -> create(parentField, childField, parentId, childId));
    }
  }

}