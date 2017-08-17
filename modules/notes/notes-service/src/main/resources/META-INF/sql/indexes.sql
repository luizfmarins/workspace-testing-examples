create index IX_69D52C6F on NOTE_Note (userName[$COLUMN_LENGTH:75$]);
create index IX_354626F9 on NOTE_Note (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_31EE17BB on NOTE_Note (uuid_[$COLUMN_LENGTH:75$], groupId);