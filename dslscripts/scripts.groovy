job('job-dsl-checkout') {
    
    scm {
        github('tetradev01/hello-world-war', 'master')
    }
  
   publishers {
        downstream 'job-dsl-compile', 'SUCCESS'
    }
    
}

mavenJob('job-dsl-compile'){
   
  customWorkspace('/var/lib/jenkins/workspace/job-dsl-checkout')
  mavenInstallation('Maven 3.3.9')
  goals('compile')
    
  publishers {
        downstream 'job-dsl-package', 'SUCCESS'
   }
}

mavenJob('job-dsl-package'){
    customWorkspace('/var/lib/jenkins/workspace/job-dsl-checkout')
    mavenInstallation('Maven 3.3.9')
    goals('package')
    
  publishers {
        downstream 'job-dsl-deploy', 'SUCCESS'
  }
}

job('job-dsl-deploy') {
    description 'Deploy app to the demo server'
    /*
     * configuring ssh plugin to run docker commands
     */
    steps{
             shell 'sshpass -p "123456" scp /var/lib/jenkins/workspace/job-dsl-checkout/target/sparkjava-hello-world-1.0.war release@10.12.108.11:/opt/tomcat/webapps/'
      }
}

listView('Listview') {
    jobs {
        regex('job-dsl-.+')
    }
    columns {
        status()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}
