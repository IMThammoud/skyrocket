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

            let row = document.getElementById("shelve-table").insertRow(i);

            let cell_name = row.insertCell(0)
            let cell_type = row.insertCell(1)
            let cell_category = row.insertCell(2)
            let cell_articleCount = row.insertCell(3)
            let cell_extra_button = row.insertCell(4)

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

            cell_articleCount.innerText = responseCount;
            cell_extra_button.value = response_array[i]["id"];
            let button_to_view_articles = document.createElement('button');
            button_to_view_articles.innerText = "einsehen";
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
            cell_extra_button.append(button_to_view_articles)
        }
    } else {
        let headlineIfNoShelvesAreAvailable = document.createElement("h5")
        headlineIfNoShelvesAreAvailable.innerHTML = "<p> Please create a shelve first: <a href=http://localhost:8080/shelve/create> create </a> </p>"
        document.getElementById("ifNoShelvesFound").appendChild(headlineIfNoShelvesAreAvailable)
    }
    // Adding header cells to the table
    // Probably need to put this at the end otherwise the tableheaders are at the last row
    let header_row = document.getElementById("shelve-table").insertRow(0)
    let header_first =  header_row.insertCell(0)
    let header_second = header_row.insertCell(1)
    let header_third = header_row.insertCell(2)
    let header_fourth = header_row.insertCell(3)
    let header_fifth = header_row.insertCell(4)

    header_row.style.fontWeight = "bold"
    header_first.innerText = "Regal"
    header_second.innerText = "Typ"
    header_third.innerText = "Kategorie"
    header_fourth.innerText = "Einträge"
    header_fifth.innerText = "Auflisten"
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