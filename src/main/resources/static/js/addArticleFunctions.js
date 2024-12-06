// This renders the shelves as options on the selection when adding a new article
async function loadShelves(){
    let request = await fetch("http://localhost:8080/shelve/retrieve",
        {
            method : "POST",
        })
    let response = await request.text()
    let shelvesAsArray = JSON.parse(response)

    for (const shelvesAsArrayKey in shelvesAsArray) {
        let option = document.createElement("option")
        option.value = shelvesAsArray[shelvesAsArrayKey]["id"]
        option.innerText = shelvesAsArray[shelvesAsArrayKey]["name"]
        document.getElementById("shelve").appendChild(option)
    }
}