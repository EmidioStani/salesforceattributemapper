/*
 * Copyright (c) 2010, Chuck Mortimore - xmldap.org
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names xmldap, xmldap.org, xmldap.com nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.identity.saml2.plugins;

import com.sun.identity.saml2.common.SAML2Exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class SalesforceAttributeMapper extends DefaultLibraryIDPAttributeMapper {

    private static final String logoutURL = "logoutURL";
    private static final String ssoStartPage = "ssoStartPage";
    private static final String startURL = "startURL";
    private static final String organization_id = "organization_id";
    private static final String portal_id = "portal_id";

    private String[] logoutURLAttributeValues;
    private String[] ssoStartPageAttributeValues;
    private String[] startURLAttributeValues;
    private String[] organizationIdAttributeValues;
    private String[] portalIdAttributeValues;


    public SalesforceAttributeMapper() {

        try {
            Properties sfdcProperties = getPropertiesFromClasspath("salesforce.properties");

            logoutURLAttributeValues = new String[1];
            ssoStartPageAttributeValues = new String[1];
            startURLAttributeValues = new String[1];
            organizationIdAttributeValues = new String[1];
            portalIdAttributeValues = new String[1];

            logoutURLAttributeValues[0] =  sfdcProperties.getProperty(logoutURL);
            ssoStartPageAttributeValues[0] =  sfdcProperties.getProperty(ssoStartPage);
            startURLAttributeValues[0] =  sfdcProperties.getProperty(startURL);
            organizationIdAttributeValues[0] =  sfdcProperties.getProperty(organization_id);
            portalIdAttributeValues[0] =  sfdcProperties.getProperty(portal_id);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected boolean isDynamicalOrIgnoredProfile(String realm) {

        return SAML2PluginsUtils.isDynamicalOrIgnoredProfile(realm);
    }

    private Properties getPropertiesFromClasspath(String propFileName) throws IOException {
        Properties props = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream == null)  throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        props.load(inputStream);
        return props;
    }

    public List getAttributes(Object session, String hostEntityID, String remoteEntityID, String realm) throws SAML2Exception {

        List attributes = super.getAttributes(session, hostEntityID, remoteEntityID, realm);
        if (logoutURLAttributeValues[0] != null) attributes.add(getSAMLAttribute(logoutURL, null, logoutURLAttributeValues));
        if (ssoStartPageAttributeValues[0] != null) attributes.add(getSAMLAttribute(ssoStartPage, null, ssoStartPageAttributeValues));
        if (startURLAttributeValues[0] != null) attributes.add(getSAMLAttribute(startURL, null, startURLAttributeValues));
        if (organizationIdAttributeValues[0] != null) attributes.add(getSAMLAttribute(organization_id, null, organizationIdAttributeValues));
        if (portalIdAttributeValues[0] != null) attributes.add(getSAMLAttribute(portal_id, null, portalIdAttributeValues));
        return attributes;

    }


}
