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
package org.openmrs.module.visit_tasks.api.security;

import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.visit_tasks.api.util.PrivilegeConstants;

/**
 * Default authorization privileges for {@link org.openmrs.OpenmrsMetadata} data services.
 */
public class BasicMetadataAuthorizationPrivileges extends BasicObjectAuthorizationPrivileges
        implements IMetadataAuthorizationPrivileges {
	@Override
	public String getRetirePrivilege() {
		return PrivilegeConstants.TASK_MANAGE_VISIT_TASK_METADATA;
	}
}