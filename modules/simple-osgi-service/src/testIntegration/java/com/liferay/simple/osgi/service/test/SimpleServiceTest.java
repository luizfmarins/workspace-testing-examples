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

package com.liferay.simple.osgi.service.test;

import com.liferay.simple.osgi.service.api.SimpleService;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Assert;
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

	@Deployment
	public static JavaArchive create() throws Exception {
		final File jarFile = new File(System.getProperty("jarFile"));

		return ShrinkWrap.createFromZipFile(JavaArchive.class, jarFile);
	}

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