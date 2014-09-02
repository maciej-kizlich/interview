
<form role="form" name="editBook" class="form-horizontal" action="/books/search" method="POST" enctype="multipart/form-data">
    <div class="form-group">
        <label for="input1" class="col-sm-2 control-label">Title</label>
        <div class="col-sm-10">
            <input id="input1" name="title" class="form-control"  placeholder="title">
        </div>
    </div>
    <div class="form-group">
        <label for="input2" class="col-sm-2 control-label">Author</label>
        <div class="col-sm-10">
            <input id="input2" name="author" class="form-control"  placeholder="author">
        </div>
    </div>
    <div class="form-group">
        <label for="input3" class="col-sm-2 control-label" title="The minimum rating to search">Rating</label>
        <div class="col-sm-10">
            <input id="input3" type="number" step="1" min="0" max="5" value="0" name="rating" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-5">
            <button type="submit" class="btn btn-default">Search</button>
        </div>
    </div>
</form>
