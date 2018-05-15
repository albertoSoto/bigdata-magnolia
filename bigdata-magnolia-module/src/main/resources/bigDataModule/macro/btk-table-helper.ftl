[#include "./btk-util.ftl"]

[#-- Analizes the main name quotations for nodes, and returns if is not hierarchical--]
[#function isKeyValid key]
[#-- avoid jcr properties, uuid, and i18n (accesed later by custom method) --]
    [#if key?index_of(":")<1 && key?index_of("@")<1 && key?index_of("_")<1]
        [#return true]
    [#else ]
        [#return false]
    [/#if]
[/#function]

[#-- iterate collection of nodes searching the key and returns it if found--]
[#function getKeyValue key valueCollection]
    [#if key?? && valueCollection?? && valueCollection?size>0]
        [#list valueCollection as k]
            [#if k?? && (k.key!"") == key && k.label??]
                [#return k.label]
            [/#if]
        [/#list]
    [/#if]
    [#return key]
[/#function]

[#macro getImage content key]
    [#assign imgItemKey = content[key]!]
    [#if imgItemKey??]
        [#assign imgRef = damfn.getAssetLink(imgItemKey,"240x180")!]
        [#if imgRef??]
        <img src="${imgRef!}" alt="${key!}"/>
        [/#if]
    [/#if]
[/#macro]

[#macro datatable object configuration id="" ]
<table id="${id!}" class="datatable table table-striped table-hover table-bordered " cellspacing="0" width="100%">
    [#if object?? && (object?is_hash || object?is_hash_ex)]
        [#assign first="true"]
        [#if !configuration.enableAjax!true]
            [#if configuration.enableFiltering!false]
                [#list cmsfn.children(object) as child ]
                    [#if child?? && (child?is_hash || child?is_hash_ex)]
                        [#if first="true"]
                            [@toheaders object=child labels=cmsfn.children(configuration.labels) configuration=configuration/]
                        <tbody>
                        [/#if]
                    <tr>
                        [#list cmsfn.children(configuration.order) as customColumn ]
                            <td>
                                [#if child[customColumn.columnKey]?? && child[customColumn.columnKey]?index_of("jcr:")<0]
                                ${i18nContent(child,customColumn.columnKey)}
                            [#else]
                                    [@getImage content=child key=customColumn.columnKey /]
                                [/#if]
                            </td>
                        [/#list]
                    </tr>
                    [/#if]
                    [#assign first="false"]
                [/#list]
            </tbody>
            [#else]
                [#list cmsfn.children(object) as child ]
                    [#if child?? && (child?is_hash || child?is_hash_ex)]
                        [#if first="true"]
                            [@toheaders object=child labels=cmsfn.children(configuration.labels) configuration=configuration/]
                        <tbody>
                        [/#if]
                    <tr>
                        [#list child?keys as k]
                            [#if child[k]?? && isKeyValid(k)]
                                <td>
                                    [#if child[k]?index_of("jcr:")<0]
                                ${i18nContent(child,k)}
                                [#else]
                                        [@getImage content=child key=k /]
                                    [/#if]
                                </td>
                            [/#if]
                        [/#list]
                    </tr>
                    [/#if]
                    [#assign first="false"]
                [/#list]
            [/#if]
        </tbody>
        [/#if]
    [/#if]
</table>
[/#macro]
[#-- creates a datatables header
TODO: abstract, simplify or functional
--]
[#macro toheaders object labels configuration]
    [#assign doFooter = configuration.enableFooter!false]
    [#if configuration.enableFiltering!false]
    <thead>
        [#list cmsfn.children(configuration.order) as customColumn ]
            [#list object?keys as k]
                [#if object[k]?? && isKeyValid(k) && customColumn.columnKey==k]
                <th>${getKeyValue(k,labels)}</th>
                [/#if]
            [/#list]
        [/#list]
    </thead>
        [#if doFooter]
        <tfoot>
            [#list cmsfn.children(configuration.order) as customColumn ]
                [#list object?keys as k]
                    [#if object[k]?? && isKeyValid(k) && customColumn.columnKey==k]
                    <th>${getKeyValue(k,labels)}</th>
                    [/#if]
                [/#list]
            [/#list]
        </tfoot>
        [/#if]
    [#else]
    <thead>
    <tr>
        [#list object?keys as k]
            [#if object[k]?? && isKeyValid(k)]
                <th>${getKeyValue(k,labels)}</th>
            [/#if]
        [/#list]
    </tr>
    </thead>
        [#if doFooter]
        <tfoot>
            [#list object?keys as k]
                [#if object[k]?? && isKeyValid(k)]
                <th>${getKeyValue(k,labels)}</th>
                [/#if]
            [/#list]
        </tfoot>
        [/#if]
    [/#if]
[/#macro]
[#--
<table id="example_1" class="table table-striped table-hover table-bordered" cellspacing="0" width="100%">
<thead>
<tr>
    <th>Name</th>
    <th>Position</th>
    <th>Office</th>
    <th>Extn.</th>
    <th>Start date</th>
</tr>
</thead>
<!--tfoot>
<tr>
    <th>Name</th>
    <th>Position</th>
    <th>Office</th>
    <th>Extn.</th>
    <th>Start date</th>
</tr>
</tfoot-->
<tbody>
<tr>
    <td>Tiger Nixon</td>
    <td>System Architect</td>
    <td>Edinburgh</td>
    <td>61</td>
    <td>2011/04/25</td>
</tr>
</tbody>
</table>
--]