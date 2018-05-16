[#include "../macro/btk-util.ftl"]
[#assign iteration = rand(0,5000)?string["0"]]
<div class="row">
[#if content.title?has_content]
    <h2>${content.title!}</h2>
[/#if]
    <div class="row">
    [#if scriptContent??]
        <div class="col-md-6">
<pre>
[#if scriptContent??]
[#list scriptContent as row]
${row!""}
[/#list]
[/#if]
</pre>
        </div>
        <div class="col-md-6">
<pre>
[#if result??]
[#list result as row]
${row!""}
[/#list]
[/#if]
</pre>
        </div>
    [#else]
        <div class="col-md-12">
            <pre>
                [#if result??]
                [#list result as row]
                ${row!""}
                [/#list]
                [/#if]
            </pre>
        </div>
    [/#if]
    </div>

</div>

