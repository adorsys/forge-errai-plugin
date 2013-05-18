Getting Started
===============
1. clone the project : <pre><code>git clone https://github.com/adorsys/forge-errai-plugin.git</code></pre>
2. start forge in a work directory
3. install the plugin, create a project and setup errai, using the plugin
<br  />
<pre>
    <code>
        forge source-plugin ../path-to-the-cloned-directory/forge-errai-plugin/
        new-project --named forge.test --type war --topLevelPackage forge.errai.test.one
        adorsyserraiplugin setup 
    </code>
</pre>
4. compile and run the project
<pre>
    <code>
        cd ./forge.test
        mvn clean install
        mvn gwt:run
    </code>
</pre>

5. open in the gwt dev-mode, open with the default browser, and **There we go !**

What is Involved
==============
1.  **jboss-forge 1.3.0.final**
2.  **maven 3.0.3**

Works out of the box
====================
1. ui-binder
2. gwt-validation framework
3. i18n
4. slf4j logging
4. gwt devmod
5. errai-jaxrs
6. errai-container
7. errai-bus
8. errai-cdi
9. errai-common

**yes ! Just make a pull request :)**
