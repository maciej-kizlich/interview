<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">



        You have returned a book successfully!
        <br/>
        Book details: <br/>
        Titile: ${book.title}<br/>
        Author: ${book.author}<br/>

        <br/></br>

        <a href="/books/list">Go to book list</a>


    </tiles:putAttribute>
</tiles:insertDefinition>

