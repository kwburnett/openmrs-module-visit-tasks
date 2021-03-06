/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.visittasks.api.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.User;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.visittasks.api.IVisitPredefinedTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;
import org.openmrs.module.visittasks.api.security.BasicMetadataAuthorizationPrivileges;
import org.openmrs.module.visittasks.api.util.VisitTasksHibernateCriteriaConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data service implementation class for {@link VisitPredefinedTask}s.
 */
@Transactional
public class VisitPredefinedTaskDataServiceImpl extends BaseMetadataDataServiceImpl<VisitPredefinedTask> implements
        IVisitPredefinedTaskDataService {
	private static final Integer MAX_PREDEFINED_TASK_NAME_CHARACTERS = 255;

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return new BasicMetadataAuthorizationPrivileges();
	}

	@Override
	protected void validate(VisitPredefinedTask entity) {}

	@Override
	public List<VisitPredefinedTask> getPredefinedTasks(final User user, final String name, final Boolean showGlobal,
	        final boolean includeRetired, PagingInfo pagingInfo) {
		if (user == null) {
			throw new IllegalArgumentException("User must be logged in");
		}

		if (StringUtils.isNotEmpty(name) && name.length() > MAX_PREDEFINED_TASK_NAME_CHARACTERS) {
			throw new IllegalArgumentException("The Predefined task name must be less than 256 characters.");
		}

		if (pagingInfo == null) {
			throw new IllegalArgumentException("PagingInfo must not be null");
		}

		return executeCriteria(VisitPredefinedTask.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (StringUtils.isNotEmpty(name)) {
					criteria.add(Restrictions.ilike(
					    VisitTasksHibernateCriteriaConstants.NAME, name, MatchMode.START));
				}

				if (!includeRetired) {
					criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.RETIRED, false));
				}

				Criterion userCriterion = Restrictions.eq(VisitTasksHibernateCriteriaConstants.USER, user);

				/**
				 * If showGlobal is set to true, return only global predefined visit tasks (Usage: manage global visit tasks
				 * page), if showGlobal is set to false, return all but global predefined visit tasks (Usage: manage my
				 * predefined visit tasks page), if showGlobal is not set, return all predefined visit tasks (Usage: visit
				 * tasks page).
				 */
				if (showGlobal != null) {
					if (!showGlobal.booleanValue()) {
						Criterion globalCriterion = Restrictions.eq(VisitTasksHibernateCriteriaConstants.GLOBAL, false);
						criteria.add(Restrictions.and(userCriterion, globalCriterion));
					} else {
						criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.GLOBAL, true));
					}
				} else {
					Criterion globalCriterion = Restrictions.eq(VisitTasksHibernateCriteriaConstants.GLOBAL, true);
					criteria.add(Restrictions.or(userCriterion, globalCriterion));
				}
			}
		});
	}
}
