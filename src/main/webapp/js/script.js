function searchToObject() {
    let pairs = window.location.search.substring(1).split("&"),
        obj = {},
        pair,
        i;

    for ( i in pairs ) {
        if ( pairs[i] === "" ) continue;

        pair = pairs[i].split("=");
        obj[ decodeURIComponent( pair[0] ) ] = decodeURIComponent( pair[1] );
    }

    return obj;
}


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



    let uriObject = searchToObject();
    if(uriObject['category'] !== undefined){
        let categories = uriObject['category'].split("_");
        let categoryBoxes = $("#filterForm input:checkbox");
        categories.forEach(category => {
            categoryBoxes.each(function (){
                if($(this).attr("value") === category){
                    $(this).attr("checked", "true");
                }
            })
        })
    }
    if(uriObject['consistency'] !== undefined){
        let categories = uriObject['consistency'].split("_");
        let categoryBoxes = $("#filterForm input:checkbox");
        categories.forEach(category => {
            categoryBoxes.each(function (){
                if($(this).attr("value") === category){
                    $(this).attr("checked", "true");
                }
            })
        })
    }


    $("#filterForm").submit(function (event){
        event.preventDefault();
        let src = "/Controller?action=filter";
        let categoryBoxes = $("#filterForm input:checkbox:checked");
        let categories = [];
        let consistencies = [];
        categoryBoxes.each(function (){
            if($(this).attr("name") === "category"){
                categories.push($(this));
            }
            if($(this).attr("name") === "consistency"){
                consistencies.push($(this));
            }
        })
        if(categories.length > 0){
            src += "&category=";
            categories.forEach(elem =>{
                src += elem.val() + "_";
            })
        }
        if(consistencies.length > 0){
            src += "&consistency=";
            consistencies.forEach(elem =>{
                src += elem.val() + "_";
            })
        }

        if(src[src.length - 1] === "_"){
            src = src.slice(1, src.length - 1);
        }
        window.location.href = src;
    })
});

