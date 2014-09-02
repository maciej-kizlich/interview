<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

        You have borrowed a book successfully!
        <br/>
        Book details: <br/>
        Title: ${book.title}<br/>
        Author: ${book.author}<br/>

        <br/></br>
        You should return this book on: <b> ${shouldReturnDate}</b>

        <a href="/books/list">Go to book list</a>

    </tiles:putAttribute>
</tiles:insertDefinition>

