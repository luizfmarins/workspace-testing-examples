/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.notes.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.notes.service.http.NoteServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.notes.service.http.NoteServiceSoap
 * @generated
 */
@ProviderType
public class NoteSoap implements Serializable {
	public static NoteSoap toSoapModel(Note model) {
		NoteSoap soapModel = new NoteSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setNoteId(model.getNoteId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setPosted(model.getPosted());
		soapModel.setItems(model.getItems());
		soapModel.setContent(model.getContent());

		return soapModel;
	}

	public static NoteSoap[] toSoapModels(Note[] models) {
		NoteSoap[] soapModels = new NoteSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static NoteSoap[][] toSoapModels(Note[][] models) {
		NoteSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new NoteSoap[models.length][models[0].length];
		}
		else {
			soapModels = new NoteSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static NoteSoap[] toSoapModels(List<Note> models) {
		List<NoteSoap> soapModels = new ArrayList<NoteSoap>(models.size());

		for (Note model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new NoteSoap[soapModels.size()]);
	}

	public NoteSoap() {
	}

	public long getPrimaryKey() {
		return _noteId;
	}

	public void setPrimaryKey(long pk) {
		setNoteId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getNoteId() {
		return _noteId;
	}

	public void setNoteId(long noteId) {
		_noteId = noteId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public boolean getPosted() {
		return _posted;
	}

	public boolean isPosted() {
		return _posted;
	}

	public void setPosted(boolean posted) {
		_posted = posted;
	}

	public int getItems() {
		return _items;
	}

	public void setItems(int items) {
		_items = items;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	private String _uuid;
	private long _noteId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private boolean _posted;
	private int _items;
	private String _content;
}