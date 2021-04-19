function searchToObject() {
    let pairs = window.location.search.substring(1).split("&"),
        obj = {},
        pair,
        i;

    for (i in pairs) {
        if (pairs[i] === "") continue;

        pair = pairs[i].split("=");
        obj[decodeURIComponent(pair[0])] = decodeURIComponent(pair[1]);
    }

    return obj;
}

function cardNumberFormatting(){
    let cardNumberField = $(".card-payment-form input[name='cardNumber']");
    let str = cardNumberField.val().replace(/\s/g, '');
    if(str.length % 4 === 0 && cardNumberField.val().length > 0){
        cardNumberField.val(cardNumberField.val() + " ");
    }
}


$(document).ready(function () {

    let value = $("#choice").attr("value");
    $("#create-medicine").css("display", "none");
    $("#update-delete-medicine").css("display", "none");
    $("#" + value + "-medicine").css("display", "block");


    $(".medicine-page-form").submit(function (event) {
        event.preventDefault();
        let id = $(".medicine-page-form input[type='hidden']").attr("value");
        let amount = $(".medicine-page-form input[type='number']").val();
        $.post(
            "/Controller?action=buy_medicine",
            {id: id, amount: amount},
            function (data) {
                $.each(data, function (key, value) {
                    if (key === "redirect") {
                        window.location.href = value;
                    } else {
                        alert(value);
                    }
                })
            }
        )
    })


    let uriObject = searchToObject();
    let keys = Object.keys(uriObject);
    keys.forEach(key => {
        if (key !== "page" && key !== "action") {
            let categories = uriObject[key].split("_");
            let categoryBoxes = $("#filterForm input");
            categories.forEach(category => {
                categoryBoxes.each(function () {
                    if ($(this).attr("value") === category) {
                        $(this).attr("checked", "true");
                    }
                })
            })
        }
    })


    $("#filterForm").submit(function (event) {
        event.preventDefault();
        let src = "/Controller?action=filter";
        let categoryBoxes = $("#filterForm input:checked");
        let arr = {};
        let i = 0;
        categoryBoxes.each(function () {
            if (arr[$(this).attr("name")] === undefined) {
                arr[$(this).attr("name")] = {};
            }
            arr[$(this).attr("name")][i++] = $(this).val();
        })

        let keys = Object.keys(arr);
        for (let i = 0; i < keys.length; i++) {
            src += "&" + keys[i] + "=";
            for(let value in arr[keys[i]]){
                src += arr[keys[i]][value] + "_";
            }
        }


        if (src[src.length - 1] === "_") {
            src = src.slice(1, src.length - 1);
        }
        window.location.href = src;
    })


});

