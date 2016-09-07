/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.visit_tasks.api;

import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataServiceTest;
import org.openmrs.module.visit_tasks.api.model.VisitTask;

import java.util.Date;

public class IVisitTaskServiceTest extends IEntityDataServiceTest<IVisitTaskService, VisitTask> {

	@Override
	public void before() throws Exception {
		super.before();

		executeDataSet(TestConstants.VISIT_TASK_DATASET);
	}

	@Override
	protected int getTestEntityCount() {
		return 5;
	}

	@Override
	public VisitTask createEntity(boolean valid) {
		VisitTask visitTask = new VisitTask();

		if (valid) {
			visitTask.setName("New Visit Task");
		}

		visitTask.setDescription("New Visit Task Description");

		return visitTask;
	}

	@Override
	public void updateEntityFields(VisitTask visitTask) {
		visitTask.setName(visitTask.getName() + " updated");
		visitTask.setDescription(visitTask.getDescription() + " updated");
		visitTask.setDateCreated(new Date());
		visitTask.setChangedBy(Context.getAuthenticatedUser());
	}

	@Override
	protected void assertEntity(VisitTask expected, VisitTask actual) {
		super.assertEntity(expected, actual);
	}
}
