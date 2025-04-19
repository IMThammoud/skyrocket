// Don't forget to set the IPv4 of the server into the fetch request later
// Dont forget to implement a cap of Shelves because there shouldt be 100 Shelves rendered
// on one Page. Would be better to dynamically load them on button press "Next" or something

let global_response_array;
let global_article_array
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
async function showShelvesAsTable() {

    let request = await fetch("http://localhost:8080/shelve/retrieve", {
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

            cell_name.innerText = response_array[i]["name"]
            cell_type.innerText = response_array[i]["type"]
            cell_category.innerText = response_array[i]["category"]

            // This endpoint expects exactly these field names: shelve_id, shelve_type
            let askForArticleCount = await fetch("http://localhost:8080/shelve/articleCount", {
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

            // Buttons of the Shelve Table
            let button_to_view_articles = document.createElement('button');
            let button_to_download_pdf = document.createElement('button');
            let button_to_download_delete_shelve = document.createElement('button');


            button_to_view_articles.innerText = "Ansehen";
            button_to_view_articles.addEventListener("click", async function () {
                // Submit ShelveID through button and get Listing template for shelve.
                // check if there are even Articles for that shelve
                let data_articles = await listArticles(cell_extra_button)
                if (data_articles.length == 0) {
                    console.log("No articles found for this shelve")
                    alert("Es sind keine Artikel in diesem Regal vorhanden.")
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

            button_to_download_delete_shelve.innerText = "Delete";
            button_to_download_delete_shelve.addEventListener("click", async function () {
                let requestOfDeletingShelve;
                    if (confirm("Are you sure?")) {
                        if (requestOfDeletingShelve = await deleteShelvesAndItsArticles(cell_extra_button)) {
                            window.location.reload();
                        }
                    } else
                        alert("Couldnt delete shelve..")
            })

            cell_extra_button.append(button_to_view_articles)
            cell_extra_button_pdf.append(button_to_download_pdf)
            cell_extra_button_delete_shelve.append(button_to_download_delete_shelve)
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

        header_row.style.fontWeight = "bold"
        header_first.innerText = "Regal"
        header_second.innerText = "Typ"
        header_third.innerText = "Kategorie"
        header_fourth.innerText = "Einträge"
        header_fifth.innerText = "Auflisten"
        header_sixth.innerText = "PDF"
        header_seventh.innerText = "Löschen"
    } else {
        let headlineIfNoShelvesAreAvailable = document.createElement("h5")
        headlineIfNoShelvesAreAvailable.innerHTML = "<p> Please create a shelve first: <a href=http://localhost:8080/shelve/create> create </a> </p>"
        document.getElementById("ifNoShelvesFound").appendChild(headlineIfNoShelvesAreAvailable)
    }

}

// Server sends ArrayList of Articles back (for this shelve)
async function listArticles(cell_extra_button){
    let request = await fetch("http://localhost:8080/shelve/get-notebooks-filtered", {
        headers : {"content-type": "application/json"},
        method : "POST",
        body : JSON.stringify({"shelve_id" : cell_extra_button.value}),
    })
    let response =  await request.json();
    return response;
}

async function downloadPDF(cell_extra_button){
    let request = await fetch("http://localhost:8080/shelve/shelve-content-to-pdf", {
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
    let request = await fetch("http://localhost:8080/shelve/delete", {
        headers : {"content-type": "application/json"},
        method : "DELETE",
        body : JSON.stringify({"shelve_id" : cell_extra_button.value}),
    })
    return request.status;
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