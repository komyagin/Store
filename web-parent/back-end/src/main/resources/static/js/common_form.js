$(document).ready(function () {
    $("#btn_cancel").on("click", function () {
        window.location = moduleURL;
    });

    $("#file_image").change(function () {
        file_size = this.files[0].size
        alert("File size: " + file_size);
        if (file_size > 1048576) {
            this.setCustomValidity("You must choose an image less than 1MB!");
            this.reportValidity()
        } else {
            this.setCustomValidity("");
            showImageThumbnail(this);
        }
    })
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result)
    };

    reader.readAsDataURL(file);
}
