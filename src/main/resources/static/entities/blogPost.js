import {personEntityName} from "./person"

export const blogPostEntityName = "blogPosts";

export default (nga) => {
  const personEntity = nga.entity(personEntityName);
  const entity = nga.entity(blogPostEntityName);
  entity.listView().fields([
    nga.field("title")
      .isDetailLink(true),
    nga.field("author", "reference")
      .targetEntity(personEntity)
      .targetField(nga.field("name")),
    nga.field("readers", "reference_many")
      .targetEntity(personEntity)
      .targetField(nga.field("name"))
      .cssClasses("hidden-xs hidden-sm")
  ]);
  entity.creationView().fields([
    nga.field("title")
      .validation({required: true}),
    nga.field("content", "wysiwyg"),
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
    nga.field("readers", "reference_many")
      .targetEntity(personEntity)
      .targetField(nga.field("name"))
      .sortField("name")
      .sortDir("DESC")
      .remoteComplete(true, {
        refreshDelay: 200,
        searchQuery(name){
          return {name}
        }
      })
  ]);
  entity.editionView().fields(entity.creationView().fields());
  return entity;
}