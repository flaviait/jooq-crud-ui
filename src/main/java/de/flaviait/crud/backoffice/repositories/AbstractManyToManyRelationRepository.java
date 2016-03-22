package de.flaviait.crud.backoffice.repositories;

import org.jooq.*;

import java.util.List;

abstract class AbstractManyToManyRelationRepository<R extends UpdatableRecord> {

  final DSLContext dslContext;
  private final Table<R> table;
  private final Field<Long> ref1;
  private final Field<Long> ref2;

  AbstractManyToManyRelationRepository(DSLContext dslContext, Table<R> table, Field<Long> ref1, Field<Long> ref2) {
    this.dslContext = dslContext;
    this.table = table;
    this.ref1 = ref1;
    this.ref2 = ref2;
  }

  List<Long> getRelatedTo1(Long id) {
    return getRelated(ref1, ref2, id);
  }

  List<Long> getRelatedTo2(Long id) {
    return getRelated(ref2, ref1, id);
  }

  private List<Long> getRelated(Field<Long> parentIdField, Field<Long> childIdField, Long id) {
    return dslContext
      .select(childIdField)
      .from(table)
      .where(parentIdField.eq(id))
      .fetch(childIdField);
  }

  void delete(Long id1, Long id2) {
    delete(ref1, ref2, id1, id2);
  }

  private void delete(Field<Long> field1, Field<Long> field2, Long id1, Long id2) {
    dslContext
      .deleteFrom(table)
      .where(field1.eq(id1).and(field2.eq(id2)))
      .execute();
  }

  R create(Long id1, Long id2) {
    return create(ref1, ref2, id1, id2);
  }

  private R create(Field<Long> field1, Field<Long> field2, Long id1, Long id2) {
    R record = dslContext.newRecord(table);
    record.setValue(field1, id1);
    record.setValue(field2, id2);
    record.store();
    return record;
  }

  void updateFor1(Long parentId, List<Long> newChildIds) {
    update(ref1, ref2, parentId, newChildIds);
  }

  void updateFor2(Long parentId, List<Long> newChildIds) {
    update(ref2, ref1, parentId, newChildIds);
  }

  private void update(Field<Long> parentField, Field<Long> childField, Long parentId, List<Long> newChildIds) {
    List<Long> oldChildIds = getRelated(parentField, childField, parentId);
    oldChildIds.stream()
      .filter(childId -> !newChildIds.contains(childId))
      .forEach(childId -> delete(parentField, childField, parentId, childId));
    newChildIds.stream()
      .filter(childId -> !oldChildIds.contains(childId))
      .forEach(childId -> create(parentField, childField, parentId, childId));
  }

}