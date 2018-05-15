<!-- This portion has been copied directly from the current html header in hello.ftl -->
<title>${content.windowTitle!content.title!}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- Use the new site templating functions to get the current site -->
[#assign site = sitefn.site()!]
<!-- Use the new site templating functions to get the theme associated with the current site -->
[#assign theme = sitefn.theme(site)!]
<!-- Loop over all configured css files and render them as links in the head portion of the page -->
[#list theme.cssFiles as cssFile]
<link rel="stylesheet" href="${cssFile.link}" media="${cssFile.media}" />
[/#list]