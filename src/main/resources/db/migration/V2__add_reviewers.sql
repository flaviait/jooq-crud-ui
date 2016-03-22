CREATE TABLE "reviewers" (
  "person" BIGINT REFERENCES "person"("id"),
  "blog_post" BIGINT REFERENCES "blog_post"("id"),
  PRIMARY KEY ("person", "blog_post")
);