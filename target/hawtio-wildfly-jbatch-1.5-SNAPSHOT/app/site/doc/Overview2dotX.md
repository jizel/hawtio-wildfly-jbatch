
The main goals for 2.x are to update to a more recent AngularJS version as well as Bootstrap and Patternfly.  We also need to enable folks to re-use parts of the console.  Currently in 1.x there's a few options to customize the existing console:

* Hide and arrange tabs via the preferences panel in the console itself
* Add a vendor.js file or load a plugin that customizes the perspective plugin's configuration
* Build a sliced up hawtio app.js via [hawtio-custom-app](https://github.com/hawtio/hawtio/tree/master/hawtio-custom-app)

Hawtio 2.x introduces the possibility of packaging up hawtio plugins as bower components.  Some advantages are:

* Dependencies for a plugin can usually be managed through bower
* Plugins can be decoupled and developed/released individually
* In the case of typescript plugins it's easier to distribute definition files for dependent plugins to use

The first bullet point is the key bit, as we can combine bower with wiredep to automatically wire in plugin js (and css!) files and dependencies into a console's index.html file.  The 2.x plugin loader also still supports discovering plugins via a JSON file or a URL that produces some JSON, so we also still have the possibility of loading plugins on the fly as well.  A console project that pulls in hawtio plugins can also define it's own plugins for some specific functionality.  The assembly project also has greater control over the layout of the navigation and pages.

## Components

Here's a rundown of the current hawtio 2.x components:

### javascript plugins

* [hawtio-core](https://github.com/hawtio/hawtio-core) - Tiny core module that contains the logging and plugin loader code.  It also contains 1 angular module that initializes some stub services that can be overridden by other plugins.  Responsible for loading plugins and bootstrapping a hawtio app
* [hawtio-core-navigation](https://github.com/hawtio/hawtio-core-navigation) - Navigation bar, also can handle sub-tabs.  Provides a nice API that allows you to define your routes and tabs in one block, or you can define your routes and tabs separately


### typescript plugins

* [hawtio-core-dts](https://github.com/hawtio/hawtio-core-dts) - A repository of typescript definition files for third-party scripts as well as any javascript plugins such as hawtio-core or hawtio-core-navigation.  Not actually a plugin, but is a dependency for any typescript plugin
* [hawtio-core-perspective](https://github.com/hawtio/hawtio-core-perspective) - Perspective selector, adds itself to the navigation bar and provides an API to plugins to configure perspectives.
* [hawtio-utilities](https://github.com/hawtio/hawtio-utilities) - A collection of helper functions used throughout hawtio, most plugins will probably depend on this module
* [hawtio-ui](https://github.com/hawtio/hawtio-ui) - The UI widgets from hawtio 1.x, including hawtio-simple-table and the editor plugin
* [hawtio-forms](https://github.com/hawtio/hawtio-forms) - The forms plugin from hawtio 1.x, used to create forms from a simple schema
* [hawtio-jmx](https://github.com/hawtio/hawtio-jmx) - The JMX and JVM plugins from hawtio 1.x as well as the tree plugin.  Now contains all jolokia initialization code as well as the Core.Workspace object service from hawtio 1.x.  Will likely be a dependency for any plugin that talks to jolokia
* [hawtio-oauth](https://github.com/hawtio/hawtio-oauth) - Provides authentication for OAuth using [OpenShift](http://openshift.github.io/) and [KeyCloak](http://keycloak.jboss.org/)
* [hawtio-dashboard](https://github.com/hawtio/hawtio-dashboard) - Provides a dashboard capability; letting any angular pages be composed into a dashboard
* [hawtio-wiki](https://github.com/hawtio/hawtio-wiki) - Provides a git based wiki
* [hawtio-kubernetes](https://github.com/hawtio/hawtio-kubernetes) - The plugin for working with [Kubernetes](http://kubernetes.io/)
* [hawtio-forge](https://github.com/hawtio/hawtio-forge) - The [JBoss Forge](http://forge.jboss.org/) plugin for hawtio so it can view and create projects and execute forge commands
* [hawtio-integration](https://github.com/hawtio/hawtio-integration) - The plugin for working with Apache Camel and ActiveMQ.
* [hawtio-java](https://github.com/hawtio/hawtio-java) - hawtio 2.x web console for Java distributed as a WAR and standalone Java JAR artefacts. 


### slush generators

* [slush-hawtio-javascript](https://github.com/hawtio/slush-hawtio-javascript) - Generates a starter jvascript plugin project that depends on hawtio-core and hawtio-core-navigation with some example plugin code.
* [slush-hawtio-typescript](https://github.com/hawtio/slush-hawtio-typescript) - Generates a starter typescript plugin project, depends on hawtio-utilities, hawtio-core and hawtio-core-navigation.


## Getting started

### working with existing projects

Git clone any of the above projects and then cd into the folder. 

Then get npm and bower to install their stuff:

    npm install
    bower install

you are now ready to run the default build with gulp

    gulp

and you should be able to open a web browser and work on the code and have things rebuild etc.

### initial setup

To get started, first off make sure you're running a relatively recent version node/npm.  [Go download it](http://nodejs.org/) and re-install/upgrade if you're not sure.  Make sure you update your npm packages with a `sudo npm update -g`.  Then install a few tools:

`npm install -g bower gulp slush slush-hawtio-javascript slush-hawtio-typescript typescript`

If you only want to develop javascript plugins then you don't really need `slush-hawtio-typescript` and `typescript`.

### first project

To create a project, first create a directory:

`mkdir my-awesome-plugin`

Then run the appropriate generator:

`slush hawtio-typescript`

*or*

`slush hawtio-javascript`

Answer the questions when prompted, the generator will then chug away and install a bunch of npm modules and bower components.  When you're back at the prompt you'll have a number of files in the current directory:

* `package.json` - Contains any node modules this project depends on.  Manage with `npm install --save-dev` and `npm uninstall --save-dev`.
* `bower.json` - Contains any bower modules this project depends on.  Also lists any main files this bower package has which is really important to fill in.  The generator already puts dist/my-awesome-plugin.js in here.
* `gulpfile.js` - This configures the build, which is done by a tool called 'gulp'.
* `plugins` - This directory contains code for an example plugin.  The build is set up to look at subdirectories, so put your plugin code in here following this kind of convention:


   ```
   plugins
         |
         -- foo
              |
              -- html
              |
              -- ts|js
   ```

* `d.ts` (typescript only) - a directory that contains definition files generated by the typescript compiler.
* `defs.d.ts` (typescript only) - a definitions file that's automatically updated at build time to include all the files under `d.ts`
* `index.html` - a simple index.html file you can use to view/test the plugin at development time.
* `dist` - The output directory of the build, files in here should generally be configured in your bower.json under "main".

To get going just start the build:

`gulp`

which will build the typescript (if applicable) and start up a web server listening on localhost:2772.  Open this in your browser and you should see a nav bar with 'Example' in it, which is the example plugin.

## IDE Tips

If you use [Intellij](https://www.jetbrains.com/idea/) or [WebStorm](https://www.jetbrains.com/webstorm/) as your IDE for working on the project then you get nice smart completion, refactoring and navigation between function calls and their source etc.

One thing that really helps when navigating from a function call to its implementation source is to exclude the **d.ts** folder of your project. This folder contains the generated TypeScript API definition files; so there's not much value in there from a navigation/completeness perspective when noodling the code.

So open File -> Project Structure then in the Modules tab select the d.ts folder and click the **Excluded** button. Then if you alt-click on a function call it will take you directly to the TypeScript/JavaScript source code of the implementation (and not prompt you to choose between the .ts and .d.ts files)

## FAQ

*My typescript code fails to compile with missing definitions for 'ng' etc...*

In your plugin code make sure you add a reference path to the `includes.ts` file under `plugins`.  This will bring in all the definitions from hawtio-core-dts, hawtio-utilities etc.

*Where can I add typescript definitions for some new dependency I brought in?*

Best place is add or replace the reference path(s) in `plugins/includes.ts`, then it'll be available to all of your plugin code.

*Can I have multiple plugins in one package?*

Yes!

*My plugin needs to talk to some other thing too, how does that happen?*

Use [hawtio-node-backend](https://www.npmjs.com/package/hawtio-node-backend) to proxy requests

Or hand-configure a proxy:

* run `npm install --save-dev proxy-middleware`
* add this code to your gulpfile at the beginning:

```javascript
var url = require('url');
var proxy = require('proxy-middleware');
```

* Change your 'connect' task to something like this:

```javascript
gulp.task('connect', ['watch'], function() {
  plugins.connect.server({
    root: '.',
    livereload: true,
    port: 2772,
    fallback: 'index.html',
    middleware: function(connect, options) {
      return [
        (function() {
          var proxyOptions = url.parse('http://localhost:8282/hawtio/jolokia');
          // proxies requests from /jolokia to the above URL
          proxyOptions.route = '/jolokia';
          return proxy(proxyOptions);
        })() ];
    }
  });
});
```

*I get weird compile errors but I didn't change anything!*

It could be a dependency got compiled with an updated typescript version.  Run `npm update` in your package to update your node modules and try again.

*Something under libs/ got messed up!*

You can just blow away `libs` and run `bower update` to re-install dependencies anytime you like.

*A bower dependency I want to install doesn't have `main` configured at all, what do I do?*

Typically for these cases it's best to run `bower install` (don't add `--save`) on the package to get the files, then copy the .js or .css files into your packages `dist` directory, then configure those js and css files in your package's `bower.json` file in the `main` attribute.  That way any package that depends on your code will get the dependent javascript automatically wired into their index.html.

*How do I work on multiple components at once?*

There's a couple options, you can use `bower link` which sets up a soft-link under libs/ to the dependent component.  However this can mess up typescript compilation.

A better option is to soft-link the `dist/hawtio-*.js` (or whatever the built javascript file is) directly.  Then you can `gulp watch` in the dependency and `gulp` in your main project and live reload will kick in whenever you change code in either.  The same approach is handy for .css files.  Then when you `bower update` after releasing the dependency the soft-link will be replaced with the updated file

*How do I enable debug logging at runtime?*

After the page is loaded, open up the javascript console and paste:

```
localStorage['logLevel'] = angular.toJson(Logger.DEBUG);
```


## Releasing

It's easy!  First make sure you have a README, and a changelog would be good.  Make sure you have a sane version in your bower.json file.  I'd also recommend updating package.json so it's consistent.  Since this is hawtio 2.x stuff all plugins should start at version 2.0.0.  Make sure you've built your plugin and check in any changes.  Then it's time to start the long winded release process of publishing a bower plugin:

1. `git tag 2.0.0`
2. `git push && git push --tags`
3. `bower register my-awesome-plugin git@github.com:hawtio/my-awesome-plugin.git`

say 'yes' in step 3 and congrats, you're done!  Now your plugin can easily be pulled in by other projects.

If you fix an issue and need to make an update it also takes a lot of steps:

1. Fix issue, update changelog etc.
2. `bower version patch`
3. `git push && git push --tags`

phew, you're done!  Now you can `bower update` in other packages that pull in my-awesome-plugin as a dependency.



