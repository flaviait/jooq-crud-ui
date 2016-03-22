import personEntity from "entities/person";
import blogPostEntity from "entities/blogPost";

angular.module("app", ["ng-admin"], (NgAdminConfigurationProvider) => {
  var nga = NgAdminConfigurationProvider;
  var admin = nga.application('jOOQ CRUD Demo');
  admin.addEntity(personEntity(nga));
  admin.addEntity(blogPostEntity(nga));
  admin.baseApiUrl("/crud/");
  nga.configure(admin);
});