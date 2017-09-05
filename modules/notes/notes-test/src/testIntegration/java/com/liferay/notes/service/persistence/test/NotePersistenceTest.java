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

package com.liferay.notes.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notes.exception.NoSuchNoteException;
import com.liferay.notes.model.Note;
import com.liferay.notes.service.NoteLocalServiceUtil;
import com.liferay.notes.service.persistence.NotePersistence;
import com.liferay.notes.service.persistence.NoteUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class NotePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.notes.service"));

	@Before
	public void setUp() throws Exception {
		_persistence = NoteUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();

		//Strings.isNullOrEmpty("abc");

		Class<?> insistenceLayerFacade = PortalUtil.class.getClassLoader()
				.loadClass("br.marins.insistence.layer.api.InsistenceLayerFacade");
		Method enableInsistenceLayer = insistenceLayerFacade.getMethod("enableInsistenceLayer");
		enableInsistenceLayer.invoke(insistenceLayerFacade);

		System.out.println("");
	}

	@After
	public void tearDown() throws Exception {
		Class<?> insistenceLayerFacade = PortalUtil.class.getClassLoader()
				.loadClass("br.marins.insistence.layer.api.InsistenceLayerFacade");
		Method rollback = insistenceLayerFacade.getMethod("rollback");
		rollback.invoke(insistenceLayerFacade);

		Method disableInsistenceLayer = insistenceLayerFacade.getMethod("disableInsistenceLayer");
		disableInsistenceLayer.invoke(insistenceLayerFacade);

		System.out.println("");
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Note note = _persistence.create(pk);

		Assert.assertNotNull(note);

		Assert.assertEquals(note.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Note newNote = addNote();

		_persistence.remove(newNote);

		Note existingNote = _persistence.fetchByPrimaryKey(newNote.getPrimaryKey());

		Assert.assertNull(existingNote);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNote();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Note newNote = _persistence.create(pk);

		newNote.setUuid(RandomTestUtil.randomString());

		newNote.setGroupId(RandomTestUtil.nextLong());

		newNote.setCompanyId(RandomTestUtil.nextLong());

		newNote.setUserId(RandomTestUtil.nextLong());

		newNote.setUserName(RandomTestUtil.randomString());

		newNote.setCreateDate(RandomTestUtil.nextDate());

		newNote.setModifiedDate(RandomTestUtil.nextDate());

		newNote.setTitle(RandomTestUtil.randomString());

		newNote.setPosted(RandomTestUtil.randomBoolean());

		newNote.setItems(RandomTestUtil.nextInt());

		newNote.setContent(RandomTestUtil.randomString());

		_notes.add(_persistence.update(newNote));

		Note existingNote = _persistence.findByPrimaryKey(newNote.getPrimaryKey());

		Assert.assertEquals(existingNote.getUuid(), newNote.getUuid());
		Assert.assertEquals(existingNote.getNoteId(), newNote.getNoteId());
		Assert.assertEquals(existingNote.getGroupId(), newNote.getGroupId());
		Assert.assertEquals(existingNote.getCompanyId(), newNote.getCompanyId());
		Assert.assertEquals(existingNote.getUserId(), newNote.getUserId());
		Assert.assertEquals(existingNote.getUserName(), newNote.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(existingNote.getCreateDate()),
			Time.getShortTimestamp(newNote.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingNote.getModifiedDate()),
			Time.getShortTimestamp(newNote.getModifiedDate()));
		Assert.assertEquals(existingNote.getTitle(), newNote.getTitle());
		Assert.assertEquals(existingNote.getPosted(), newNote.getPosted());
		Assert.assertEquals(existingNote.getItems(), newNote.getItems());
		Assert.assertEquals(existingNote.getContent(), newNote.getContent());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByUsername() throws Exception {
		_persistence.countByUsername(StringPool.BLANK);

		_persistence.countByUsername(StringPool.NULL);

		_persistence.countByUsername((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Note newNote = addNote();

		Note existingNote = _persistence.findByPrimaryKey(newNote.getPrimaryKey());

		Assert.assertEquals(existingNote, newNote);
	}

	@Test(expected = NoSuchNoteException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Note> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("NOTE_Note", "uuid", true,
			"noteId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"title", true, "posted", true, "items", true, "content", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Note newNote = addNote();

		Note existingNote = _persistence.fetchByPrimaryKey(newNote.getPrimaryKey());

		Assert.assertEquals(existingNote, newNote);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Note missingNote = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNote);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Note newNote1 = addNote();
		Note newNote2 = addNote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNote1.getPrimaryKey());
		primaryKeys.add(newNote2.getPrimaryKey());

		Map<Serializable, Note> notes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notes.size());
		Assert.assertEquals(newNote1, notes.get(newNote1.getPrimaryKey()));
		Assert.assertEquals(newNote2, notes.get(newNote2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Note> notes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Note newNote = addNote();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNote.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Note> notes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notes.size());
		Assert.assertEquals(newNote, notes.get(newNote.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Note> notes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Note newNote = addNote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNote.getPrimaryKey());

		Map<Serializable, Note> notes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notes.size());
		Assert.assertEquals(newNote, notes.get(newNote.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = NoteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Note>() {
				@Override
				public void performAction(Note note) {
					Assert.assertNotNull(note);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Note newNote = addNote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Note.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("noteId",
				newNote.getNoteId()));

		List<Note> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Note existingNote = result.get(0);

		Assert.assertEquals(existingNote, newNote);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Note.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("noteId",
				RandomTestUtil.nextLong()));

		List<Note> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Note newNote = addNote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Note.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("noteId"));

		Object newNoteId = newNote.getNoteId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("noteId",
				new Object[] { newNoteId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNoteId = result.get(0);

		Assert.assertEquals(existingNoteId, newNoteId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Note.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("noteId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("noteId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Note newNote = addNote();

		_persistence.clearCache();

		Note existingNote = _persistence.findByPrimaryKey(newNote.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingNote.getUuid(),
				ReflectionTestUtil.invoke(existingNote, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingNote.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingNote, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected Note addNote() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Note note = _persistence.create(pk);

		note.setUuid(RandomTestUtil.randomString());

		note.setGroupId(RandomTestUtil.nextLong());

		note.setCompanyId(RandomTestUtil.nextLong());

		note.setUserId(RandomTestUtil.nextLong());

		note.setUserName(RandomTestUtil.randomString());

		note.setCreateDate(RandomTestUtil.nextDate());

		note.setModifiedDate(RandomTestUtil.nextDate());

		note.setTitle(RandomTestUtil.randomString());

		note.setPosted(RandomTestUtil.randomBoolean());

		note.setItems(RandomTestUtil.nextInt());

		note.setContent(RandomTestUtil.randomString());

		_notes.add(_persistence.update(note));

		return note;
	}

	private List<Note> _notes = new ArrayList<Note>();
	private NotePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}