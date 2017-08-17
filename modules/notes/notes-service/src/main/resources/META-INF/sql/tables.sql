create table NOTE_Note (
	uuid_ VARCHAR(75) null,
	noteId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	posted BOOLEAN,
	items INTEGER,
	content VARCHAR(75) null
);