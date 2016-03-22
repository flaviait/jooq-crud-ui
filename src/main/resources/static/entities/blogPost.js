import {personEntityName} from "./person"

export const blogPostEntityName = "blogPosts";

export default (nga) => {
  const personEntity = nga.entity(personEntityName);
  const entity = nga.entity(blogPostEntityName);
  entity.listView().fields([
    nga.field("id"),
    nga.field("title"),
    nga.field("content"),
    nga.field("author", "reference")
      .targetEntity(personEntity)
      .targetField(nga.field("name")),
    nga.field("reviewers", "reference_many")
      .targetEntity(personEntity)
      .targetField(nga.field("name"))
  ]);
  entity.creationView().fields([
    nga.field("title"),
    nga.field("content"),
    nga.field("author", "reference")
      .targetEntity(personEntity)
      .targetField(nga.field("name"))
      .sortField("name")
      .sortDir("DESC")
      .validation({required: true})
      .remoteComplete(true, {
        refreshDelay: 200,
        searchQuery(search){
          return {search}
        }
      }),
    nga.field("reviewers", "reference_many")
      .targetEntity(personEntity)
      .targetField(nga.field("name"))
      .sortField("name")
      .sortDir("DESC")
      .validation({required: true})
      .remoteComplete(true, {
        refreshDelay: 200,
        searchQuery(search){
          return {search}
        }
      })
  ]);
  entity.editionView().fields(entity.creationView().fields());
  return entity;
}