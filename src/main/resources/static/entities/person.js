export const personEntityName = "persons";

export default (nga) => {
  let entity = nga.entity(personEntityName);
  entity.listView().fields([
    nga.field("id"),
    nga.field("name")
  ]);
  entity.creationView().fields([
    nga.field("name")
      .validation({required: true})
  ]);
  entity.editionView().fields(entity.creationView().fields());
  return entity;
}