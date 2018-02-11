jenkins.model.Jenkins.theInstance.getProjects().each { job ->
    if (!job.name.contains('deletejob')) {
        println job.name
        job.delete()
    }
}
