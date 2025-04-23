// Don't forget to set the IPv4 of the server into the fetch request later
// Dont forget to implement a cap of Shelves because there shouldt be 100 Shelves rendered
// on one Page. Would be better to dynamically load them on button press "Next" or something

// Using this object as long as i dont know how to use objects from other files
let notebookObject = {
    "pk_id":"",
    "name":"",
    "amount":"",
    "type":"",
    "description":"",
    "price_when_bought":"",
    "selling_price":"",
    "fk_shelve_id":"",
    "brand":"",
    "model_nr":"",
    "cpu":"",
    "ram":"",
    "storage_in_gb":"",
    "display_size_inches":"",
    "operating_system":"",
    "battery_capacity_health":"",
    "keyboard_layout":"",
    "side_note":""
}



async function submitArticleAndWaitForResponseWhileOnShelveView(notebookObject) {
    console.log(notebookObject)
    let request =  await fetch("/add/article/receiveArticle", {
        headers: {"Content-Type": "application/json"},
        method: "POST",
        body: JSON.stringify(notebookObject)
    })
    let response = await request.text()
    if (response === "success") {
        alert("Article added to shelve")
    } else {
        alert("Server does not like your form. ")
    }
}

// Gets the Shelve_ID from the function that lists the shelve in the dashboard. This is a very important FIELD
let shelve_id_as_cell_value

let filtered_notebook_object_table_headrow = {
    "name":"",
    "amount":"",
    "type":"",
    "selling_price":"",
    "brand":"",
    "model_nr":"",
    "cpu":"",
    "ram":"",
    "storage_in_gb":"",
    "display_size_inches":"",
    "operating_system":"",
    "battery_capacity_health":"",
    "keyboard_layout":"",
    "side_note":""
}
let extra_button = document.createElement("button");
extra_button.id = "extra_button";
let extra_button_pdf = document.createElement("button");
extra_button_pdf.id = "extra_button_pdf";

let extra_button_delete_shelves = document.createElement("button");
extra_button_delete_shelves.id = "extra_button_delete_shelves";
extra_button_delete_shelves.style.color = "red";
// extra_button.innerHTML = '<img style ="width: 30px" src="/icons/icons-skyrocket/eye-solid.svg" alt="listing-button">';
extra_button.innerText = "View Articles";

// I cycle through the shelves creating a table  and give every Cell that contains a button the id of the shelve that is
// currently iterated through as cell.value
// So when a button is clicked on any cell it can use the shelveID that is specifically for this row.
async function showShelvesAsTable() {

    let request = await fetch("/shelve/retrieve", {
        method: "POST",
    })
    let response = await request.text();

    let response_array = JSON.parse(response)
    if (response_array.length > 0){

        // Cycling through the retrieved shelves and building a table from it
        for (let i = 0; i < response_array.length; i++) {
            let shelve_id_of_current_iteration = response_array[i]["id"];
            console.log(shelve_id_of_current_iteration);
            let shelve_type_of_current_iteration = response_array[i]["type"];

            let shelveTable = document.getElementById("shelve-table");
            shelveTable.style = "margin:auto";
            let row = document.getElementById("shelve-table").insertRow(i);


            let cell_name = row.insertCell(0)
            let cell_type = row.insertCell(1)
            let cell_category = row.insertCell(2)
            let cell_articleCount = row.insertCell(3)
            let cell_extra_button = row.insertCell(4)
            let cell_extra_button_pdf = row.insertCell(5)
            let cell_extra_button_delete_shelve = row.insertCell(6)
            let cell_extra_button_add_to_shelve = row.insertCell(7)

            cell_name.innerText = response_array[i]["name"]
            cell_type.innerText = response_array[i]["type"]
            cell_category.innerText = response_array[i]["category"]

            // This endpoint expects exactly these field names: shelve_id, shelve_type
            let askForArticleCount = await fetch("/shelve/articleCount", {
                headers: {"content-type": "application/json"},
                method: "POST",
                body: JSON.stringify({
                    shelve_id: shelve_id_of_current_iteration,
                    shelve_type: shelve_type_of_current_iteration,
                }),
            })
            let responseCount = await askForArticleCount.json()

            // Assigning the shelve_id to the cells in which the buttons will be
            cell_articleCount.innerText = responseCount;
            cell_extra_button.value = response_array[i]["id"];
            cell_extra_button_pdf.value = response_array[i]["id"];
            cell_extra_button_delete_shelve.value = response_array[i]["id"];
            cell_extra_button_add_to_shelve.value = response_array[i]["id"];

            // Buttons of the Shelve Table
            let button_to_view_articles = document.createElement('button');
            let button_to_download_pdf = document.createElement('button');
            let button_to_delete_shelve = document.createElement('button');
            let button_to_add_to_shelve = document.createElement('button');


            button_to_view_articles.innerText = "View";
            button_to_view_articles.addEventListener("click", async function () {
                // Submit ShelveID through button and get Listing template for shelve.
                // check if there are even Articles for that shelve
                let data_articles = await listArticles(cell_extra_button)
                if (data_articles.length == 0) {
                    console.log("No articles found for this shelve")
                    alert("No Articles found for this shelve.")
                } else {
                    console.log(data_articles)
                    console.log("amount of keys (columns): ", Object.keys(data_articles[0]).length);
                    replaceShelveDashboardWithArticleList(Object.keys(data_articles).length, data_articles);
                }
            })

            button_to_download_pdf.innerText = "Download";
            button_to_download_pdf.addEventListener('click', async function () {
                let shelve_as_pdf_data = await downloadPDF(cell_extra_button)
                if (shelve_as_pdf_data == null) {
                    console.log("No PDF was created. Shelve is probably empty")
                    alert("Shelve is probably empty. No PDF was created.")
                } else {

                    // I create a Link and trigger a Download
                    let downloadLink = document.createElement("a");
                    downloadLink.href = URL.createObjectURL(shelve_as_pdf_data);
                    downloadLink.download = "shelve_as_pdf.pdf";
                    downloadLink.click();
                    console.log("PDF-Creation successful.")
                    alert("PDF-Creation successful.")
                }
            })

            button_to_delete_shelve.innerText = "Delete";
            button_to_delete_shelve.addEventListener("click", async function () {
                let requestOfDeletingShelve;
                    if (confirm("Are you sure?")) {
                        if (requestOfDeletingShelve = await deleteShelvesAndItsArticles(cell_extra_button)) {
                            window.location.reload();
                        }
                    } else
                        alert("Couldnt delete shelve..")
            })

            button_to_add_to_shelve.innerText = "Add";
            button_to_add_to_shelve.addEventListener("click", async function () {
                let request = await askForTemplateToAddArticleWhileOnShelveView(cell_extra_button_add_to_shelve)
                document.getElementById("shelve-table").remove()
                document.getElementById("shelves-dashboard-heading").innerText = "Add Article from Shelve-Dashboard";

            })

            cell_extra_button.append(button_to_view_articles)
            cell_extra_button_pdf.append(button_to_download_pdf)
            cell_extra_button_delete_shelve.append(button_to_delete_shelve)
            cell_extra_button_add_to_shelve.append(button_to_add_to_shelve)
        }

        // Adding header cells to the table
        // Probably need to put this at the end otherwise the tableheaders are at the last row
        let header_row = document.getElementById("shelve-table").insertRow(0)
        let header_first =  header_row.insertCell(0)
        let header_second = header_row.insertCell(1)
        let header_third = header_row.insertCell(2)
        let header_fourth = header_row.insertCell(3)
        let header_fifth = header_row.insertCell(4)
        let header_sixth = header_row.insertCell(5)
        let header_seventh = header_row.insertCell(6)
        let header_eigth = header_row.insertCell(7)

        header_row.style.fontWeight = "bold"
        header_first.innerText = "Shelf"
        header_second.innerText = "Type"
        header_third.innerText = "Category"
        header_fourth.innerText = "Entries"
        header_fifth.innerText = "List"
        header_sixth.innerText = "PDF"
        header_seventh.innerText = "Delete"
        header_eigth.innerText = "Article"

    } else {
        let headlineIfNoShelvesAreAvailable = document.createElement("h5")
        headlineIfNoShelvesAreAvailable.innerHTML = "<p> Please create a shelve first: <a href='/shelve/create'> Create </a> </p>"
        document.getElementById("ifNoShelvesFound").appendChild(headlineIfNoShelvesAreAvailable)
    }

}

// Server sends ArrayList of Articles back (for this shelve)
async function listArticles(cell_extra_button){
    let request = await fetch("/shelve/get-notebooks-filtered", {
        headers : {"content-type": "application/json"},
        method : "POST",
        body : JSON.stringify({"shelve_id" : cell_extra_button.value}),
    })
    let response =  await request.json();
    return response;
}

async function downloadPDF(cell_extra_button){
    let request = await fetch("/shelve/shelve-content-to-pdf", {
        headers : {"content-type": "application/json"},
        method : "POST",
        body : JSON.stringify({"shelve_id" : cell_extra_button.value}),
    })
    if (request.ok) {
        return await request.blob()
    } else
        return null;
}

async function deleteShelvesAndItsArticles(cell_extra_button){
    let request = await fetch("/shelve/delete", {
        headers : {"content-type": "application/json"},
        method : "DELETE",
        body : JSON.stringify({"shelve_id" : cell_extra_button.value}),
    })
    return request.status;
}

async function askForTemplateToAddArticleWhileOnShelveView(cell_extra_button_add_to_shelve) {

    // When clicking on add article to shelve on UI
    let request = await fetch("/add/article/check-shelve-type",
        {
            headers: {"content-type": "application/json;charset=UTF8"},
            method: "POST",
            body: JSON.stringify({"shelve": cell_extra_button_add_to_shelve.value}),
        })
    let response = await request.text()
    console.log("This was the response from server after clicking add button on shelveview: " + response)
    switch (response) {
        // Add some other cases in future and mirror the cases with the endpoint on the server, storing global ShelveID to use it in request function
        case "notebook":
            console.log("ShelveID of selected shelve:" + cell_extra_button_add_to_shelve.value)
            //document.getElementById("replaceableWithJsIfAddButtonClicked").remove()
            build_notebook_form(cell_extra_button_add_to_shelve)

        //case "smartphone":
        //    shelveIdOfSelectedShelve = shelve_id_as_cell_value
        //    console.log("ShelveID of selected shelve:" + shelveIdOfSelectedShelve)
        //    document.getElementById("replaceableWithJsIfAddButtonClicked").remove()
        //    build_smartphone_form()

        default:
            console.log("No suitable type returned by the server for template generation.")
            break;
    }
}

function replaceShelveDashboardWithArticleList(number_of_keys, data_articles) {
    document.getElementById("shelve-table").remove()
    let new_table = document.createElement("table")
    document.getElementById("table-article").style.fontSize = "75%"
    // Amount of keys (columns) based on shelve type.
    let keys_amount = Object.keys(data_articles[0]).length;
    // To have a collection of keys so i can cycle through them if needed.
    let keys = Object.keys(data_articles[0])
    for (let j  = 0; j < number_of_keys; j++) {
        console.log("j:"+ j)
        let row = new_table.insertRow(j);

        for (let i = 0; i < keys_amount ; i++) {
            // Insert cells into each row based on amount of Keys(columns) of a shelvetype like notebook
            // Example: Notebook shelve has 19-Columns so it creates 19 cells in a row.
            // "j" iterates through the objects that i get from data_articles while keys[i] iterates through each key
            // This builds each entry in the table view under the HeaderRow.
            row.insertCell(i).innerText = data_articles[j][keys[i]];
        }
    }
    // Creating TableHeaderRow at end with index=0 so all other rows move up the index +1
    // Doing this, the header will stay on top.
    let headerRow = new_table.insertRow(0);
    headerRow.style.fontWeight = "bold";
    for (let i = 0; i < Object.keys(data_articles[0]).length; i++) {
        headerRow.insertCell(i).innerText = Object.keys(data_articles[0]).at(i);
    }
    document.getElementById("table-article").append(new_table);

}

// This method is a duplicate of the build_notebook_form() from /js/addArticleFunctions.js
// Until i know how to import functions from other js files, this will stay.
function build_notebook_form(cell_extra_button){
    // Building the form
    let formDiv = document.createElement("div")
    formDiv.id = "Js-built-form"

    let mainElement = document.createElement("main")
    mainElement.id = "main-element"
    let buttonSpace = document.getElementById("Button-space-in-form")

    let templateForm = document.createElement("form")
    templateForm.id = "template-form"

    // Create label and link its for property to the input form
    let label_notebookbrand = document.createElement("label")
    label_notebookbrand.innerText = "Brand"
    label_notebookbrand.htmlFor = "notebook-brand"
    let notebookBrand = document.createElement("input")
    notebookBrand.id = "notebook-brand"
    notebookBrand.placeholder = "Lenovo"
    notebookBrand.type = "text"

    let label_notebookname = document.createElement("label")
    label_notebookname.innerText = "Name"
    label_notebookname.htmlFor = "notebook-name"
    let notebookName = document.createElement("input")
    notebookName.id = "notebook-name"
    notebookName.placeholder = "Thinkpad T14 Gen5"
    notebookName.type = "text"

    let label_notebookAmount = document.createElement("label")
    label_notebookAmount.innerText = "Amount"
    label_notebookAmount.htmlFor = "notebook-amount"
    let notebookAmount = document.createElement("input")
    notebookAmount.id = "notebook-amount"
    notebookAmount.placeholder = "1..10.."
    notebookAmount.size = 20
    notebookAmount.type = "number"

    // Need to add selection with types for the typefield
    let label_notebookType = document.createElement("label")
    label_notebookType.innerText = "Type of Notebook"
    label_notebookType.htmlFor = "notebook-typ"
    let notebookType = document.createElement("select")
    notebookType.id = "notebook-type"
    notebookType.placeholder = "Standard, Convertable"
    notebookType.type = "select-one"
    let optionStandard = document.createElement("option")
    optionStandard.innerText = "Standard"
    optionStandard.value = "standard"
    let optionConvertible = document.createElement("option")
    optionConvertible.innerText = "Convertible"
    optionConvertible.value = "convertible"
    let optionTwoInOneTablet = document.createElement("option")
    optionTwoInOneTablet.innerText = "2-in-1-tablet"
    optionTwoInOneTablet.value = "2-in-1-tablet"
    notebookType.appendChild(optionStandard)
    notebookType.appendChild(optionConvertible)
    notebookType.appendChild(optionTwoInOneTablet)

    let label_notebookDescription = document.createElement("label")
    label_notebookDescription.innerText = "Short Description"
    label_notebookDescription.htmlFor = "notebook-description"
    let notebookDescription = document.createElement("textarea")
    notebookDescription.id = "notebook-description"
    notebookDescription.placeholder = "Light and portable Workstation that works efficient and has all-day batterylife."
    notebookDescription.type = "text"

    let label_notebookPriceWhenBought = document.createElement("label")
    label_notebookPriceWhenBought.innerText = "Bought for €"
    label_notebookPriceWhenBought.htmlFor = "notebook-price-when-bought"
    let notebookPriceWhenBought = document.createElement("input")
    notebookPriceWhenBought.id = "notebook-price-when-bought"
    notebookPriceWhenBought.placeholder = "429,99"
    notebookPriceWhenBought.min = "0"
    notebookPriceWhenBought.size = 30
    notebookPriceWhenBought.type = "number"

    let label_notebookSellingPrice = document.createElement("label")
    label_notebookSellingPrice.innerText = "Selling For €"
    label_notebookSellingPrice.htmlFor = "notebook-selling-price"
    let notebookSellingPrice = document.createElement("input")
    notebookSellingPrice.id = "notebook-selling-price"
    notebookSellingPrice.placeholder = "429,99"
    notebookSellingPrice.min = "0"
    notebookSellingPrice.size = 30
    notebookSellingPrice.type = "number"

    let label_notebookModelNumber = document.createElement("label")
    label_notebookModelNumber.innerText = "Modelnumber"
    label_notebookModelNumber.htmlFor = "notebook-modelnumber"
    let notebookModelNumber = document.createElement("input")
    notebookModelNumber.id = "notebook-modelnumber"
    notebookModelNumber.placeholder = "20SUB-QSYYC"
    notebookModelNumber.size = 50
    notebookModelNumber.type = "text"

    let label_notebookCpu = document.createElement("label")
    label_notebookCpu.innerText = "CPU / Processor"
    label_notebookCpu.htmlFor = "notebook-cpu"
    let notebookCpu = document.createElement("input")
    notebookCpu.id = "notebook-cpu"
    notebookCpu.placeholder = "Intel Core i5 or i5 1235u"
    notebookCpu.size = 30
    notebookCpu.type = "text"

    let label_notebookRam = document.createElement("label")
    label_notebookRam.innerText = "RAM / Memory in GBs"
    label_notebookRam.htmlFor = "notebook-ram"
    let notebookRam = document.createElement("input")
    notebookRam.id = "notebook-ram"
    notebookRam.placeholder = "16"
    notebookRam.min = 0
    notebookRam.max = 1000
    notebookRam.size = 30
    notebookRam.type = "number"

    let label_notebookStorageInGigs = document.createElement("label")
    label_notebookStorageInGigs.innerText = "Storage size in GB"
    label_notebookStorageInGigs.htmlFor = "notebook-storage"
    let notebookStorageInGigs = document.createElement("input")
    notebookStorageInGigs.id = "notebook-storage"
    notebookStorageInGigs.placeholder = "1000GB"
    notebookStorageInGigs.min = 0
    notebookStorageInGigs.size = 30
    notebookStorageInGigs.type = "number"
    //notebookStorageInGigs.pattern = "[0-9]{3}-[0-9]{3}-[0-9]{4}"


    let label_notebookDisplaySizeInInches = document.createElement("label")
    label_notebookDisplaySizeInInches.innerText = "Display in inches"
    label_notebookDisplaySizeInInches.htmlFor = "notebook-displaySize"
    let notebookDisplaySizeInInches = document.createElement("input")
    notebookDisplaySizeInInches.id = "notebook-displaySize"
    notebookDisplaySizeInInches.min = "0"
    notebookDisplaySizeInInches.placeholder = "14"
    notebookDisplaySizeInInches.size = 30
    notebookDisplaySizeInInches.type = "number"

    let label_notebookOS = document.createElement("label")
    label_notebookOS.innerText = "Operating System"
    label_notebookOS.htmlFor = "notebook-os"

    let notebookOS = document.createElement("select")
    notebookOS.id = "notebook-os"
    notebookOS.placeholder = "Windows 11"
    notebookOS.innerHTML = "Betriebssystem"
    let osOptionWindows10 = document.createElement("option")
    osOptionWindows10.value = "Windows10"
    osOptionWindows10.innerText = "Windows10"
    let osOptionWindows11 = document.createElement("option")
    osOptionWindows11.value = "Windows11"
    osOptionWindows11.innerText = "Windows11"
    let osOptionMac = document.createElement("option")
    osOptionMac.value = "MacOS"
    osOptionMac.innerText = "MacOS"
    let osOptionLinux = document.createElement("option")
    osOptionLinux.value = "LinuxOS"
    osOptionLinux.innerText = "LinuxOS"
    notebookOS.appendChild(osOptionWindows10)
    notebookOS.appendChild(osOptionWindows11)
    notebookOS.appendChild(osOptionMac)
    notebookOS.appendChild(osOptionLinux)

    let label_notebookBatteryHealth = document.createElement("label")
    label_notebookBatteryHealth.innerText = "Batteryhealth in %"
    label_notebookBatteryHealth.htmlFor = "notebook-batteryhealth"
    let notebookBatteryHealth = document.createElement("input")
    notebookBatteryHealth.id = "notebook-batteryhealth"
    notebookBatteryHealth.placeholder = "80%"
    notebookBatteryHealth.size = 50
    notebookBatteryHealth.max = 100
    notebookBatteryHealth.min = 1
    notebookBatteryHealth.type = "number"

    let label_notebookKeyboardLayout = document.createElement("label")
    label_notebookKeyboardLayout.innerText = "Keyboard Layout"
    label_notebookKeyboardLayout.htmlFor = "notebook-keyboardlayout"
    let notebookKeyboardLayout = document.createElement("input")
    notebookKeyboardLayout.id = "notebook-keyboardlayout"
    notebookKeyboardLayout.placeholder = "Deutsch"
    notebookKeyboardLayout.size = 300
    notebookKeyboardLayout.type = "text"

    let label_notebookSideNote = document.createElement("label")
    label_notebookSideNote.innerText = "Short note .."
    label_notebookSideNote.id = "notebook-sidenote"
    let notebookSideNote = document.createElement("textarea")
    notebookSideNote.id = "notebook-sidenote"
    notebookSideNote.placeholder = "Hier mehr infos hinterlassen falls noch etwas offen ist."
    notebookSideNote.size = 300
    notebookSideNote.type = "text"

    let submitNotebookButton = document.createElement("button")
    submitNotebookButton.id = "submitNotebookButton"
    submitNotebookButton.style = "margin-top: 5%"
    submitNotebookButton.innerHTML = "Save Article"

    // submitNotebookButton.onclick
    //Adding eventlistener is necessary to lock all values into notebookObject and submit it
    submitNotebookButton.addEventListener("click", function() {
        notebookObject.pk_id = ""
        notebookObject.fk_shelve_id = cell_extra_button.value
        notebookObject.name = notebookName.value
        notebookObject.amount = notebookAmount.value
        notebookObject.type = notebookType.value
        notebookObject.description = notebookDescription.value
        notebookObject.price_when_bought = notebookPriceWhenBought.value
        notebookObject.selling_price = notebookSellingPrice.value
        notebookObject.brand = notebookBrand.value
        notebookObject.model_nr = notebookModelNumber.value
        notebookObject.cpu = notebookCpu.value
        notebookObject.ram = notebookRam.value
        notebookObject.storage_in_gb = notebookStorageInGigs.value
        notebookObject.display_size_inches = notebookDisplaySizeInInches.value
        notebookObject.operating_system = notebookOS.value
        notebookObject.battery_capacity_health = notebookBatteryHealth.value
        notebookObject.keyboard_layout = notebookKeyboardLayout.value
        notebookObject.side_note = notebookSideNote.value;
        console.log("---------------------------------notebook.type----------------------------------------"  +  notebookObject)
        submitArticleAndWaitForResponseWhileOnShelveView(notebookObject);}

    );

    //////////////////////////////////////////////////////////////////////
    // attach the elements to an element by using id in "add-article.html"
    templateForm.appendChild(label_notebookbrand)
    templateForm.appendChild(notebookBrand)

    templateForm.appendChild(label_notebookname)
    templateForm.appendChild(notebookName)

    templateForm.appendChild(label_notebookAmount)
    templateForm.appendChild(notebookAmount)

    templateForm.appendChild(label_notebookType)
    templateForm.appendChild(notebookType)

    templateForm.appendChild(label_notebookDescription)
    templateForm.appendChild(notebookDescription)

    templateForm.appendChild(label_notebookPriceWhenBought)
    templateForm.appendChild(notebookPriceWhenBought)

    templateForm.appendChild(label_notebookSellingPrice)
    templateForm.appendChild(notebookSellingPrice)

    templateForm.appendChild(label_notebookModelNumber)
    templateForm.appendChild(notebookModelNumber)

    templateForm.appendChild(label_notebookCpu)
    templateForm.appendChild(notebookCpu)

    templateForm.appendChild(label_notebookRam)
    templateForm.appendChild(notebookRam)

    templateForm.appendChild(label_notebookStorageInGigs)
    templateForm.appendChild(notebookStorageInGigs)

    templateForm.appendChild(label_notebookDisplaySizeInInches)
    templateForm.appendChild(notebookDisplaySizeInInches)

    templateForm.appendChild(label_notebookOS)
    templateForm.appendChild(notebookOS)

    templateForm.appendChild(label_notebookBatteryHealth)
    templateForm.appendChild(notebookBatteryHealth)

    templateForm.appendChild(label_notebookKeyboardLayout)
    templateForm.appendChild(notebookKeyboardLayout)

    templateForm.appendChild(label_notebookSideNote)
    templateForm.appendChild(notebookSideNote)

    formDiv.appendChild(templateForm)
    formDiv.appendChild(submitNotebookButton)

    mainElement.appendChild(formDiv)

    let toAttachTheFormToShelveView = document.getElementById("replaceableWithJsIfAddButtonClicked")
    toAttachTheFormToShelveView.append(mainElement)
}