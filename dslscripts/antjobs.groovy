job('example') {
    steps {
        ant {
            targets(['clean', 'init', 'compile', 'war'])
            buildFile('build.xml')
            javaOpt('-Xmx1g')
            javaOpts(['-Dprop2=value2', '-Dprop3=value3'])
            antInstallation('Ant 1.8')
        }
    }
}