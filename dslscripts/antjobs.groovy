job('antbuild') {
    scm {
        github('ferdynice/helloworld-war', 'master')
    }

    steps {
        ant {
            targets(['clean', 'init', 'compile', 'war'])
            prop('version', '0.1-dev')
            prop('source', '1.7')
            prop('target', '1.7')
            buildFile('build.xml')
            javaOpt('-Xmx1g')
            javaOpts(['-Dprop2=value2', '-Dprop3=value3'])
            antInstallation('Ant 1.9.3')
        }
    }

    publishers {
        downstream 'deploy', 'SUCCESS'
    }
}

job('deploy') {
    description 'Deploy app to the demo server'
    /*
     * configuring ssh plugin to run docker commands
     */
    steps{
             shell 'sshpass -p "123456" scp /var/lib/jenkins/workspace/antbuild/build/helloworld-0.1-dev.war deploy@192.168.44.8:/var/lib/tomcat7/webapps'
      }
}