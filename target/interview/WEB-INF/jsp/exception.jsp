<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">


        <p>
        <div>Sorry but there is some problem with last request:</div>
        </p>

        <div>Original request: ${uri}</div>
        <div>Message: ${exception.message}</div>


        <!--
        exception.
        -->



    </tiles:putAttribute>
</tiles:insertDefinition>