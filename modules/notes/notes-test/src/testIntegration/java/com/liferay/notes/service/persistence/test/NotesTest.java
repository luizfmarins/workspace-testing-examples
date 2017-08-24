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
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.notes.model.Note;
import com.liferay.notes.service.NoteLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Liferay
 */
@RunWith(Arquillian.class)
public class NotesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.notes.service"));

	@Test
	public void addSomeNotes() throws Exception {
		Stream.of("aaa","bbb","ccc").forEach( title -> {
			Note note = NoteLocalServiceUtil.createNote(CounterLocalServiceUtil.increment());
			note.setTitle(title);
			
			NoteLocalServiceUtil.addNote(note);
			
			_notes.add(note);
		});
		
		Assert.assertEquals(3, NoteLocalServiceUtil.getNotesCount());
	}
	
	@Test
	public void countExitsingNotes() throws Exception {
		Assert.assertEquals(0, NoteLocalServiceUtil.getNotesCount());
	}

	@DeleteAfterTestRun
	private List<Note> _notes = new ArrayList<>();
}