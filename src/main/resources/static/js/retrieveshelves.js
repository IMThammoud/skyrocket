// Don't forget to set the IPv4 of the server into the fetch request later

async function retrieveShelves() {
    let request =   await fetch("http://localhost:8080/shelve/retrieve", {
                                method : "POST",
    })
    let response =  await request.text();

    let response_array = JSON.parse(response);


    for (let i = 0; i < response_array.length ; i++) {

        let row = document.getElementById("shelve-table").insertRow(i);

        let cell_name = row.insertCell(0)
        let cell_id = row.insertCell(1)

        cell_name.innerText = response_array[i]["name"]
        cell_id.innerText = response_array[i]["id"]

    }

}