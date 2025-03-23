-- スキーマ作成
CREATE SCHEMA IF NOT EXISTS sample;

-- テーブル作成
CREATE TABLE sample.table_one (
    user_id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER,
    sex INTEGER
);

CREATE TABLE sample.table_two (
    id VARCHAR(10) NOT NULL,
    date TIMESTAMP NOT NULL,
    value INTEGER NOT NULL
);

CREATE TABLE sample.table_three (
    id VARCHAR(10) PRIMARY KEY,
    name TEXT NOT NULL
);

-- 外部キー設定
ALTER TABLE sample.table_two
ADD CONSTRAINT fk_table_two_id
FOREIGN KEY (id) REFERENCES sample.table_three(id);

-- デフォルトスキーマ設定
ALTER ROLE batchuser SET search_path TO sample;
