// purging views
jenkins.model.Jenkins.theInstance.getViews().each {
  view ->
  if (view.name != 'All' && view.name != 'Jenkins') {
    jenkins.model.Jenkins.theInstance.deleteView(view)
  }
}
