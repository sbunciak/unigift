==============================
UniGift jBPM Project - How to
==============================
0. About

UniGift jBPM Project is a School project for BPM course tought at Masaryk University, Brno. Contains business processes as a result of business analysis, including implementation of chosen business processes in jBPM 5.4

1. Prerequisites

This script assumes you have Java JDK 1.5+ (set as JAVA_HOME), and Ant 1.7+ installed. If you don't, use the following links to download and install them:

Java: http://java.sun.com/javase/downloads/index.jsp

Ant: http://ant.apache.org/bindownload.cgi

2. Download the installer

First of all, you need to download the installer. There are two versions, a full installer (which already contains a lot of the dependencies that are necessary during the installation) and a minimal installer (which only contains the installer and will download all dependencies). In general, it is probably best to download the full installer: jBPM-{version}-installer-full.zip

Link to full installer: http://sourceforge.net/projects/jbpm/files/jBPM%205/jbpm-5.4.0.Final/jbpm-5.4.0.Final-installer-full.zip/download

3. Demo setup

The easiest way to get started is to simply run the installation script to install the demo setup. Simply go into the install folder and run:

ant install.demo

This will:

    Download JBoss AS
    Download Eclipse
    Install Drools Guvnor into JBoss AS
    Install jBPM Designer into JBoss AS
    Install the jBPM console into JBoss AS
    Install the jBPM Eclipse plugin
    Install the Drools Eclipse plugin
    
4. Open Eclipse

Navigate to directory with your jBPM installation and run 

./eclipse/eclipse

This will open the eclipse installed along with the jBPM distribution. Now import this project: File - Import ... - Existing Project into Workspace - Navigate to the folder with this project. Select it, right click - Run As - JUnit test and all tests should pass.

NOTE: In case of some compilation problems update the eclipse IDE (add this url http://download.eclipse.org/releases/indigo to update sites and use 'Check for updates...' function).
 
