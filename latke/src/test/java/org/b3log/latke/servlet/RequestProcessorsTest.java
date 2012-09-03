/*
 * Copyright (c) 2009, 2010, 2011, 2012, B3log Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.latke.servlet;

import java.util.HashSet;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.b3log.latke.testhelper.VirtualObject;

/**
 * test for {@link RequestProcessors}.
 * @author <a href="mailto:wmainlove@gmail.com">Love Yao</a>
 * @version 1.0.0.1, Sep 3, 2012
 */
public class RequestProcessorsTest extends TestCase {

    /**
     * test {@link RequestProcessors}.invoke method.
     */
    @SuppressWarnings("unchecked")
    public void testInvoke() {
        
        final VirtualObject service = new VirtualObject("org.b3log.latke.testhelper.MockService");
        
        final VirtualObject processorMethod = new VirtualObject("org.b3log.latke.servlet.RequestProcessors$ProcessorMethod");
        processorMethod.setValue("uriPattern", "/string");
        processorMethod.setValue("withContextPath", false);
        processorMethod.setValue("uriPatternMode", URIPatternMode.ANT_PATH);
        processorMethod.setValue("method", HTTPRequestMethod.GET.name());
        processorMethod.setValue("processorClass", service.getInstanceClass());
        processorMethod.setValue("processorMethod", service.getInstanceMethod("getString", new Class<?>[]{}));
       
        
        final VirtualObject  requestProcessors = new VirtualObject("org.b3log.latke.servlet.RequestProcessors");
        @SuppressWarnings("rawtypes")
        final HashSet hashSet  = (HashSet<?>) requestProcessors.getValue("processorMethods");
        hashSet.add(processorMethod.getInstance());
        

       final String requestURI = "/string";
       final String ret = (String) RequestProcessors.invoke(requestURI, "/", "GET", new HTTPRequestContext());
       Assert.assertEquals("string", ret);
        
    }
}
