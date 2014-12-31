Using the SWT jar file:

Download the version of the SWT that is appropriate for your machine from
http://www.eclipse.org/swt/

Note that 32-bit versions and 64-bit versions are not the same.

There are several versions of the SWT jar file in this folder. Make a copy of the
desired jar file  (for example, swt-3.7.1-win32-win32-x86.jar) and rename the copy
to swt.jar. By doing this, it is not necessary to modify the classpath batch file
at the top level.

If team members are developing on different platforms, write a simple script for
each platform that deletes the existing swt.jar file, makes a copy of the desired
platform jar file and rename it to swt.jar.

To build under Eclipse, make sure that the jar file is added under the project
properties/Build Path/Libraries (click on Add JARs...).