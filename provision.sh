#!/usr/bin/env bash
sudo apt-get update
echo "Installing Apache.."
sudo apt-get install -y apache2
echo "Installing Tomcat.."
sudo apt-get install -y tomcat9
echo "Installing Git.."
sudo apt-get install -y git
echo "Installing Maven.."
sudo apt-get install -y maven
echo "Installing Java 10.."
echo "oracle-java10-installer shared/accepted-oracle-license-v1-1 select true" | sudo /usr/bin/debconf-set-selections
sudo add-apt-repository ppa:linuxuprising/java
sudo apt-get update
sudo apt-get install -y oracle-java10-installer
sudo apt-get install -y oracle-java10-set-default
cd /vagrant
mvn clean package
cd target
java -jar contactfilter-0.0.1-SNAPSHOT.jar