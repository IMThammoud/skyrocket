// This renders the shelves as options on the selection when adding a new article
// It fetches a json array uses its key value pairs for the options in selection tab of "add-article.html"
let shelveIdOfSelectedShelve

async function loadShelves(){
    let request = await fetch("http://localhost:8080/shelve/retrieve",
        {
            method : "POST",
        })
    let response = await request.text()
    let shelvesAsArray = JSON.parse(response)

    for (const shelvesAsArrayKey in shelvesAsArray) {
        let option = document.createElement("option")
        option.id = "optionIdentifier"
        // Giving the option an ID so i can pass it to the server to get the right article-template back.
        option.value = shelvesAsArray[shelvesAsArrayKey]["id"]
        option.innerText = shelvesAsArray[shelvesAsArrayKey]["name"]
        document.getElementById("shelve").appendChild(option)

        console.log("Current retrieved Shelve: " + option.innerText)
    }

}

// Asking for returning shelvetype of given shelveid, based on that i render the template with js then.
async function askForTemplateTypeUsingShelveID() {


    let request = await fetch("http://localhost:8080/add/article/to_shelve",
        {
            headers : {"content-type" : "application/json;charset=UTF8"},
            method : "POST",
            body : JSON.stringify({ "shelve": document.getElementById("shelve").value}),
        })
    let response = await request.text()
    switch (response) {
        case "notebook":
            shelveIdOfSelectedShelve = document.getElementById("shelve").value
            console.log("ShelveID of selected shelve:" + shelveIdOfSelectedShelve)

            // Building the form with Javascript
            let templateForm = document.createElement("form")
            templateForm.id = "template-form"
            templateForm.method = "POST"

            let modelinput = document.createElement("input")
            modelinput.id = "model-input"
            modelinput.placeholder = "T14 G5"
            modelinput.type = "text"


            // attach the elements to an element by using id in "add-article.html"
            templateForm.appendChild(modelinput)
            document.getElementById("hund").appendChild(templateForm)

        default:
            return null;
    }
}