/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.simple.osgi.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.simple.osgi.service.api.SimpleService;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Gregory Amerson
 */
@RunWith(Arquillian.class)
public class SimpleServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule = 
		new LiferayIntegrationTestRule();
	
	@Test
	public void testSimpleService() throws Exception {
		BundleContext bundleContext = FrameworkUtil.getBundle(
			SimpleService.class).getBundleContext();

		ServiceReference<SimpleService> serviceReference =
			bundleContext.getServiceReference(SimpleService.class);

		Assert.assertNotNull(serviceReference);

		SimpleService simpleService = bundleContext.getService(
			serviceReference);

		Assert.assertNotNull(simpleService);

		Assert.assertEquals("I am simple!", simpleService.getValue());
	}

}