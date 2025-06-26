/* // Ill keep this out for the moment and only focus on 1 article at a time in freemode invoice creator.
let counter = 1
// used to increment IDs of the new article box that is added everytime user clicks the button..
// I could cap the counter at a specific number to prevent spamming of the button.

function add_article_box_and_increment_counter() {
    // This element spawns into an Article Box in invoice_dashboard when a button is pressed to add another article
    // only make button add article when counter is less than 6
    if(counter < 6) {
        let article_box = document.createElement("div")
        article_box.id = "article_box_" + counter
        article_box.innerHTML =
            "<br><br>" +
            "<div>" +
            "<label for='Invoice_Id_" + counter + "'>Invoice ID</label>" +
            "<input type='text' id='Invoice_Id_" + counter + "' placeholder='0001IFM'>" +
            "</div>" +

            "<div>" +
            "<label for='text_area_" + counter + "'>Article / Service description</label>" +
            "<textarea id='text_area_" + counter + "' cols='30' rows='10' placeholder='1x Article with...'></textarea>" +
            "</div>" +

            "<div>" +
            "<label for='price_" + counter + "'>Price in € (tax included)</label>" +
            "<input type='number' id='price_" + counter + "'>" +
            "</div>" +

            "<div>" +
            "<label for='tax_percent'>Tax in %</label>" +
            "<input type='number' id='tax_percent_'+ counter + placeholder='19%'>" +
            "</div>";

        document.getElementById("outer_div_4_to_add_articlebox_to").append(article_box)
        console.log("added articlebox: " + article_box.id)
        counter++
        alert("Added article box.")
    } else {
        alert("Already added 5 Article Boxes.")
    }

}

function invoice_pdf_download() {
    let list_article_boxes = document.getElementById("outer_div_4_to_add_articlebox_to")

    let amount_of_child_elements = list_article_boxes.children.length
    console.log("Amount of child_elements:  " + amount_of_child_elements)

    document.getElementById("Invoice_Id").value

    console.log(Array.from(list_article_boxes.children)["article_box_0"])

}
*/

async function submit_to_get_invoice_freemode() {
    let request = await fetch("/invoice/pdf-freemode", {
        headers : {"content-type": "application/json"},
        method: "POST",
        body: JSON.stringify({
            /////////////////////////////////////////////////////////////
            // Creator Info
            "date" : document.getElementById("date_invoice_freemode").value,
            "name_creator" : document.getElementById("Name").value,
            "address_creator" : document.getElementById("Address").value,
            "zip_code_creator" : document.getElementById("Zip_Code").value,
            "city_creator" : document.getElementById("City").value,
            "tel_creator" : document.getElementById("Tel.").value,
            //////////////////////////////////////////////////////////////
            // Customer Info
            "name_customer" : document.getElementById("Name_Customer").value,
            "address_customer" : document.getElementById("Address_Customer").value,
            "zip_code_customer" : document.getElementById("Zip_Code_Customer").value,
            "city_customer" : document.getElementById("City_Customer").value,
            "tel_customer" : document.getElementById("Tel._Customer").value,
            //////////////////////////////////////////////////////////////
            // Article / Service Offering
            "invoice_id" : document.getElementById("Invoice_Id").value,
            "text_area_article" : document.getElementById("text_area_article").value,
            "price" : document.getElementById("price_article_0").value,
            "tax_percentage" : document.getElementById("tax_percent").value
        })
    })

    let pdf = await request.blob()
    // Check for 200, if not yell at the user with alerts.
    if (request.status === 200) {
        let download_link = document.createElement("a")
        download_link.href = URL.createObjectURL(pdf)
        download_link.download = document.getElementById("Name_Customer").value + "_" + "invoice" + ".pdf"
        download_link.click()
    } else {
        console.log("Server: " + pdf.status)
        alert("Mandatory: Invoice ID, Your Name, Customer Name, Price and Tax.")
    }
}


async function loadShelvesForAutoInvoiceSelect(){
    let request = await fetch("/shelve/retrieve",
        {
            method : "POST",
        })
    let response = await request.text()
    let shelvesAsArray = JSON.parse(response)
    console.log("Length of shelve-Array" + shelvesAsArray.length)

    if( shelvesAsArray.length > 0) {
        for (const shelvesAsArrayKey in shelvesAsArray) {
            let option = document.createElement("option")
            option.id = "optionIdentifier"
            console.log("testing if blockblabalbalab shelves should be created now haha!")
            // Giving the option an ID so i can pass it to the server to get the right article-template back.
            option.value = shelvesAsArray[shelvesAsArrayKey]["id"]
            option.innerText = shelvesAsArray[shelvesAsArrayKey]["name"]
            document.getElementById("shelve").appendChild(option)

            console.log("Current retrieved Shelve: " + option.innerText)
        }
    } else {
        document.getElementById("shelve").remove()
        document.getElementById("buttonForAskingForTemplate").remove()
        document.getElementById("labelForSelectElement").remove()
        let whenNoShelvesFoundForTemplateCreation = document.createElement("h5")
        whenNoShelvesFoundForTemplateCreation.innerHTML = "<p>Please create a shelve first: <a href='/shelve/create'> Create </a></p>"
        document.getElementById("replaceableWithJS").appendChild(whenNoShelvesFoundForTemplateCreation)
    }
    }

    async function loadArticlesOfShelveAsSelection(){

        document.getElementById("article").remove()
        let article_selection = document.createElement("select")
        article_selection.id = "article"
        document.getElementById("select_section").appendChild(article_selection)



        let request = await fetch("/shelve/get-notebooks-unfiltered",
            {
                headers : {"content-type" : "application/json;charset=UTF8"},
                method : "POST",
                body : JSON.stringify({"shelve_id" : document.getElementById("shelve").value})
            })
        let response = await request.text()
        let articlesAsArray = JSON.parse(response)
        console.log("Length of shelve-Array" + articlesAsArray.length)

        if( articlesAsArray.length > 0) {
            for (const articlesAsArrayKey in articlesAsArray) {
                let option = document.createElement("option")
                option.id = "optionIdentifierArticle"
                console.log("testing if blockblabalbalab shelves should be created now haha!")
                // Giving the option an ID so i can pass it to the server to get the right article-template back.
                option.value = articlesAsArray[articlesAsArrayKey]["id"]
                option.innerText = articlesAsArray[articlesAsArrayKey]["name"]
                article_selection.appendChild(option)

                console.log("retrieved article: " + option.innerText)
            }
        } else {
            document.getElementById("article").remove()
           }
        }
