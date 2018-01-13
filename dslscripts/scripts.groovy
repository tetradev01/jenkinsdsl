// purge jobs
jenkins.model.Jenkins.theInstance.getProjects().each { job ->
    if (!job.name.contains('seed') && !job.name.contains('Jenkins')) {
        job.delete()
    }
}

job('job-dsl-checkout') {
    
    scm {
        github('IBM-Cloud/java-helloworld', 'master')
    }
  
   publishers {
        publishCloneWorkspace '**', '', 'Any', 'TAR', true, null
        downstream 'job-dsl-compile', 'SUCCESS'
    }
    
}

mavenJob('job-dsl-compile'){
  scm{
    cloneWorkspace 'job-dsl-checkout'
  }
  
  mavenInstallation('Maven 3.3.9')
  goals('compile')
  
  publishers {
        publishCloneWorkspace '**', '', 'Any', 'TAR', true, null
        downstream 'job-dsl-package', 'SUCCESS'
   }
}

mavenJob('job-dsl-package'){
  scm{
    cloneWorkspace 'job-dsl-checkout'
  }
  
  mavenInstallation('Maven 3.3.9')
  goals('package')
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

job('job-dsl-deploy') {
    description 'Deploy app to the demo server'
    /*
     * configuring ssh plugin to run docker commands
     */
    configure { project ->
        project / buildWrappers / 'org.jvnet.hudson.plugins.SSHBuildWrapper' {
            siteName 'vagrant@192.168.44.8:22'
            postScript """
ifconfig
"""
        }
    }
}

