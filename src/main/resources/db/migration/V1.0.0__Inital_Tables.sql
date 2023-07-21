CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE project (
    id uuid PRIMARY KEY,
    name text,
    description text
);

CREATE TABLE tag (
    id uuid PRIMARY KEY,
    name text,
    hex_color VARCHAR(6)
);

CREATE TABLE field (
    id uuid PRIMARY KEY,
    project_id uuid NOT NULL,
    title text,
    content text,
    CONSTRAINT field_fk FOREIGN KEY (project_id)
        REFERENCES project (id)
);

CREATE TABLE project_tag (
    project_id uuid,
    tag_id uuid,
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project(id),
    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tag(id),
    CONSTRAINT project_tag_pk PRIMARY KEY (project_id, tag_id)
)