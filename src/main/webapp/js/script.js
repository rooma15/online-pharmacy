$( document ).ready(function() {

    let value = $("#choice").attr("value");
    $("#create-medicine").css("display", "none");
    $("#update-delete-medicine").css("display", "none");
    $("#" + value + "-medicine").css("display", "block");



    $(".medicine-page-form").submit(function (event){
        event.preventDefault();
        let id = $(".medicine-page-form input[type='hidden']").attr("value");
        let amount = $(".medicine-page-form input[type='number']").val();
        $.post(
            "/Controller?action=buy_medicine",
            {id: id, amount: amount},
            function (data){
                $.each(data, function (key, value){
                    if(key === "redirect"){
                        window.location.href = value;
                    }else{
                        alert(value);
                    }
                })
            }
        )
    })
});

