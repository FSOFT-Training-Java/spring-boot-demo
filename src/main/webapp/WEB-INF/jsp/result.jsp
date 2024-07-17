<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>

<h1>${message}</h1>


<input type="file" name="" id="fileInput">
<br/>
<button id="btnUpload">Upload</button>
<br>
<img src="" alt="" width="300" id="image">
<script>
    const btnUpload = document.querySelector('#btnUpload');
    btnUpload.addEventListener('click', () => {
        const fileInput = document.querySelector('#fileInput');
        const file = fileInput.files[0];
        if(file) {
            const formData = new FormData();
            formData.append('file', file);
            fetch('http://localhost:8080/api/file/uploadFile', {
                method: 'POST',
                body: formData,
            }).then(response => response.json()).then(data => {
                console.log('Success:', data);
                document.querySelector('#image').src = data.fileDownloadUri;
            }).catch(error => {
                console.error('Error:', error);
                alert('File upload failed!');
            });
        } else {
            alert('Please select a file to upload.');
        }
    });
</script>
</body>
</html>