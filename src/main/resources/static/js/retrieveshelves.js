async function retrieveShelves() {
    let request =   await fetch("http://localhost:8080/shelve/retrieve", {
                                method : "POST",
    })
    let response =  await request.text()
    console.log(response.toString())
    return document.getElementById("heading").innerText = response

}