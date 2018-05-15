[#macro getCssLinks site]
    [#assign theme = sitefn.theme(site)!]
    [#list theme.cssFiles as cssFile]
    <link rel="stylesheet" href="${cssFile.link}" media="${cssFile.media}"/>
    [/#list]
[/#macro]
[#macro getJSLinks site]
    [#assign theme = sitefn.theme(site)!]
    [#list theme.jsFiles as jsFile]
    <script src="${jsFile.link}"></script>
    [/#list]
[/#macro]