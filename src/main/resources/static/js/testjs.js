async function sendDummy() {
    let request =  await fetch("http://localhost:8080/testjs")
    let response = await request.text()


    return document.getElementById("heading").innerText = response

}