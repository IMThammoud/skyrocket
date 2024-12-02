// Don't forget to set the IPv4 of the server into the fetch request later
// Dont forget to implement a cap of Shelves because there shouldt be 100 Shelves rendered
// on one Page. Would be better to dynamically load them on button press "Next" or something
async function retrieveShelves() {
    let request =   await fetch("http://localhost:8080/shelve/retrieve", {
                                method : "POST",
    })
    let response =  await request.text();

    let response_array = JSON.parse(response);


    // Cycling through the retrieved shelves and building a table from it
    for (let i = 0; i < response_array.length ; i++) {

        let row = document.getElementById("shelve-table").insertRow(i);


        let cell_name = row.insertCell(0)
        let cell_id = row.insertCell(1)
        let cell_type = row.insertCell(2)
        let cell_category = row.insertCell(3)

        cell_name.innerText = response_array[i]["name"]
        cell_id.innerText = response_array[i]["id"]
        cell_type.innerText = response_array[i]["type"]
        cell_category.innerText = response_array[i]["category"]

    }
    // Adding header cells to the table
    // Probably need to put this at the end otherwise the tableheaders are at the last row
    let header_row = document.getElementById("shelve-table").insertRow(0)
    let header_first =  header_row.insertCell(0)
    let header_second = header_row.insertCell(1)
    let header_third = header_row.insertCell(2)
    let header_fourth = header_row.insertCell(3)

    header_row.style.fontWeight = "bold"
    header_first.innerText = "Regal"
    header_second.innerText = "ID"
    header_third.innerText = "Typ"
    header_fourth.innerText = "Kategorie"



}