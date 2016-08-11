function getFiles(filesData){
    for(var i in filesData){
        var elem = $("<a>");
        elem.attr("href", "files/" + filesData[i].filename);
        if(filesData[i].comment == null) {
            elem.text(filesData[i].originalFilename);
        }else{
            elem.text(filesData[i].comment);
        }
        $("#fileList").append(elem);
        var deleteFile = filesData[i].filename;
        var deleteForm = $('<form action="/delete" method="post">\
            <input type="password" placeholder="(Optional) Enter deletion Password" name="password"/>\
            <input id="fileToDelete" type="text" name="fileName" hidden/>\
            <button type="submit">Delete</button>\
            </form> <br/>');
        $("#fileList").append(deleteForm);
        $("input[id=fileToDelete]").val(deleteFile);
    }
}
$.get("/files", getFiles);
