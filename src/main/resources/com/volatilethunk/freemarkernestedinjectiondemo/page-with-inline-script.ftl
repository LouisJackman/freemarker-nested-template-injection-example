<html><body><script>

<#--
  A totally contrived scenario. That said, server-side templating is often combined with inline scripts for features
  like analytics and setting up "global" features in server side templates baselines in Single-Page Applications.
-->

var accessToken = ${value};
setupAnalytics(accessToken);

</script></body></html>