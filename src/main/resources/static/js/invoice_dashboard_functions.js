// used to increment IDs of the new article box that is added everytime user clicks the button..
// I could cap the counter at a specific number to prevent spamming of the button.
let counter = 1

function add_article_box_and_increment_counter() {
    // This element spawns into an Article Box in invoice_dashboard when a button is pressed to add another article
    let article_box = document.createElement("div")
    article_box.id = "article_box_" + counter
    article_box.innerHTML =
        "            <div>\n" +
        "                <label for=\"Invoice_Id_"+ counter +"\">Invoice ID</label>\n" +
        "                <input type=\"text\" id='Invoice_Id_"+ counter +"' placeholder=\"0001IFM\">\n" +
        "            </div>\n" +
        "\n" +
        "            <div>\n" +
        "                <label for=\"text_area_"+ counter +"\">Article / Service description</label>\n" +
        "                <textarea name=\"\" id=\"text_area_"+ counter +"\" cols=\"30\" rows=\"10\" placeholder=\"1x Article with...\"></textarea>\n" +
        "            </div>\n" +
        "            <div>\n" +
        "                <label for=\"price_"+ counter +"\">Price in € (tax included)</label>\n" +
        "                <input type=\"number\" id=\"price_"+ counter +"\">\n" +
        "            </div>\n"
    document.getElementById("outer_div_4_to_add_articlebox_to").append(article_box)
    console.log("added articlebox: " + article_box.id)
    counter++
}