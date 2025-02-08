// Don't forget to set the IPv4 of the server into the fetch request later
// Dont forget to implement a cap of Shelves because there shouldt be 100 Shelves rendered
// on one Page. Would be better to dynamically load them on button press "Next" or something
let global_response_array;
let extra_button = document.createElement("button");
extra_button.id = "extra_button";
// extra_button.innerHTML = '<img style ="width: 30px" src="/icons/icons-skyrocket/eye-solid.svg" alt="listing-button">';
extra_button.innerText = "View Articles";
async function showShelvesAsTable() {
    let request =   await fetch("http://localhost:8080/shelve/retrieve", {
                                method : "POST",
    })
    let response =  await request.text();

    let response_array = JSON.parse(response);
    console.log(response_array[0]["id"]);

    // Cycling through the retrieved shelves and building a table from it
    for (let i = 0; i < response_array.length ; i++) {
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
        let askForArticleCount =   await fetch("http://localhost:8080/shelve/articleCount", {
            headers : {"content-type": "application/json"},
            method : "POST",
            body : JSON.stringify({
                shelve_id : shelve_id_of_current_iteration,
                shelve_type: shelve_type_of_current_iteration,
            }),
        })
        let responseCount = await askForArticleCount.json()

        cell_articleCount.innerText = responseCount;
        cell_extra_button.value = response_array[i]["id"];
        let button_to_view_articles = document.createElement('button');
        button_to_view_articles.innerText = "einsehen";
        button_to_view_articles.addEventListener("click", function(){
            // Submit ShelveID through button and get Listing template for shelve.
            console.log("Clicked on button for row: "+ cell_extra_button.value);
        })
        cell_extra_button.append(button_to_view_articles)




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