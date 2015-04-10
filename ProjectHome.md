When using Salesforce.com as a SAML Service Provider, there is often need to have custom attributes in an Attribute Assertion.   These attributes are static, and OpenSSO doesn't provide an out-of-the-box capability to do this; it requires a plugin.    This project provides said plugin

Once you download, there is an ant directory with a build.xml.    This will generated a ZIP file in the build directory.   You need to copy this zip to the opensso directory, and unzip it.   It will create a couple new files in the classes directory.   

1) A plugin which adds in what you need:   WEB-INF/classes/com/sun/identity/saml2/plugins/SalesforceAttributeMapper.class

2) A properties file which can be configured with the 5 salesforce properties: WEB-INF/classes/salesforce.properties

Configure the properties files with the desired values. 

Then, in OpenSSO, under federation, choose the Idp, and under Assertion Processing, set the following as the AttributeMapper:

com.sun.identity.saml2.plugins.SalesforceAttributeMapper

Details on SAML Attribute Assertion formats can be found here:

https://na1.salesforce.com/help/doc/en/sso_saml_assertion_examples.htm   (note you may need to change the instance url )