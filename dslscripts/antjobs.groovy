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
            antInstallation('Ant 1.8')
        }
    }
}