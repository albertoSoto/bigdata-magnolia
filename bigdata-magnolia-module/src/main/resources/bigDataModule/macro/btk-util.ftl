[#-- backoffice templating kit macro utils by Alberto Soto Fernandez --]
[#function i18nContent node property defaultLocale="es"]
[#-- i18n evaluation for data container --]
    [#assign langSufix = ""]
    [#if cmsfn.language()!= defaultLocale]
        [#assign langSufix = "_"+cmsfn.language()!]
    [/#if]
    [#return node[property+langSufix!""]!node[property]!""]
[/#function]

[#function object2json object]
[#-- works with templatingfunctions--]
    [#return jsonfn.fromChildNodesOf(object).addAll().exclude("mgnl.*", "jcr.*",":","@.*")]
[/#function]

[#-- macro for dumping a Freemarker object to JSON. Take care! Recursive!
Not fully working with magnolia naming for nodes--]
[#macro objectToJsonMacro object]
    [@compress single_line=true]
        [#if object?is_hash || object?is_hash_ex]
            [#assign first="true"]
        {
            [#list object?keys as key]
            [#--if key?string?index_of(":")<1--]
                [#if first="false"],[/#if]
                [#assign value][@objectToJsonMacro object=object[key] /][/#assign]
            "${key?trim}" : ${value?trim}
                [#assign first="false"]
            [#--/#if--]
            [/#list]
        }
        [#elseif object?is_enumerable]
            [#assign first="true"]
        [
            [#list object as item]
                [#if first="false"],[/#if]
                [#assign value][@objectToJsonMacro object=item /][/#assign]
            ${value?trim}
                [#assign first="false"]
            [/#list]
        ]
        [#else]
        "${object?trim}"
        [/#if]
    [/@compress]
[/#macro]
[#--
* Generates a "random" integer between min and max (inclusive)
*
* Note the values this function returns are based on the current
* second the function is called and thus are highly deterministic
* and SHOULD NOT be used for anything other than inconsequential
* purposes, such as picking a random image to display.
--]
[#function rand min max]
    [#local now = .now?long?c /]
    [#local randomNum = _rand!0 +
    ("0." + now?substring(now?length-1) + now?substring(now?length-2))?number /]
    [#if (randomNum > 1)]
        [#assign _rand = randomNum % 1 /]
    [#else]
        [#assign _rand = randomNum /]
    [/#if]
    [#return (min + ((max - min) * _rand))?round /]
[/#function]

[#macro compress_single_line]
    [#local captured][#nested][/#local]
${ captured?replace("^\\s+|\\s+$|\\n|\\r", "", "rm") }
[/#macro]

[#--  asoto: solving jquery loading problem as shown at https://goo.gl/rSFJQh --]
[#macro setDeferJquery openJsTags=true]
    [#if openJsTags]
    <script type="text/javascript">
    [/#if]
if (!window.deferAfterjQueryLoaded) {
    window.deferAfterjQueryLoaded = [];
    Object.defineProperty(window, "$", {
        set: function (value) {
            window.setTimeout(function () {
                $.each(window.deferAfterjQueryLoaded, function (index, fn) {
                    fn();
                });
            }, 0);
            Object.defineProperty(window, "$", {value: value});
        },
        configurable: true
    });
}
    [#if openJsTags]
    </script>
    [/#if]
[/#macro]