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
            document.getElementById("replaceableWithJS").remove()
            build_notebook_form()

        default:
            return null;
    }

    function build_notebook_form(){
        // Building the form
        let formDiv = document.createElement("div")
        formDiv.id = "Js-built-form"

        let mainElement = document.getElementById("main-element")

        let templateForm = document.createElement("form")
        templateForm.id = "template-form"

        let notebookBrand = document.createElement("input")
        notebookBrand.id = "notebook-brand"
        notebookBrand.placeholder = "Lenovo"
        notebookBrand.type = "text"

        let notebookName = document.createElement("input")
        notebookName.id = "notebook-name"
        notebookName.placeholder = "Thinkpad T14 Gen5"
        notebookName.type = "text"

        let notebookAmount = document.createElement("input")
        notebookAmount.id = "notebook-amount"
        notebookAmount.placeholder = "1..10.."
        notebookAmount.size = 50
        notebookAmount.type = "number"

        // Need to add selection with types for the typefield
        let notebookType = document.createElement("select")
        notebookType.id = "notebook-type"
        notebookType.placeholder = "Standard, Convertable"
        notebookType.type = "text"
        let optionStandard = document.createElement("option")
        optionStandard.value = "standard"
        let optionConvertable = document.createElement("option")
        optionConvertable.value = "convertable"
        let optionTwoInOneTablet = document.createElement("option")
        optionTwoInOneTablet.value = "2-in-1-tablet"
        notebookType.appendChild(optionStandard)
        notebookType.appendChild(optionConvertable)
        notebookType.appendChild(optionTwoInOneTablet)

        let notebookDescription = document.createElement("textarea")
        notebookDescription.id = "notebook-description"
        notebookDescription.placeholder = "Light and portable Workstation that works efficient and has all-day batterylife."
        notebookDescription.type = "text"

        let notebookPriceWhenBought = document.createElement("input")
        notebookPriceWhenBought.id = "notebook-price"
        notebookPriceWhenBought.placeholder = "329,99€"
        notebookPriceWhenBought.min = 0
        notebookPriceWhenBought.size = 100
        notebookPriceWhenBought.type = "number"

        let notebookSellingPrice = document.createElement("input")
        notebookSellingPrice.id = "notebook-selling-price"
        notebookSellingPrice.placeholder = "429,99€"
        notebookPriceWhenBought.min = 0
        notebookSellingPrice.size = 100
        notebookSellingPrice.type = "number"

        let notebookModelNumber = document.createElement("input")
        notebookModelNumber.id = "notebook-modelnumber"
        notebookModelNumber.placeholder = "20SUB-QSYYC"
        notebookModelNumber.size = 200
        notebookModelNumber.type = "text"

        let notebookCpu = document.createElement("input")
        notebookCpu.id = "notebook-cpu"
        notebookCpu.placeholder = "Intel Core i5 or i5 1235u"
        notebookCpu.size = 300
        notebookCpu.type = "text"

        let notebookRam = document.createElement("input")
        notebookRam.id = "notebook-name"
        notebookRam.placeholder = "16"
        notebookRam.min = 0
        notebookRam.max = 1000
        notebookRam.size = 50
        notebookRam.type = "number"

        let notebookStorageInGigs = document.createElement("input")
        notebookStorageInGigs.id = "notebook-storage"
        notebookStorageInGigs.placeholder = "1000GB"
        notebookStorageInGigs.min = 0
        notebookStorageInGigs.size = 100
        notebookStorageInGigs.type = "number"

        let notebookDisplaySizeInInches = document.createElement("input")
        notebookDisplaySizeInInches.id = "notebook-displaySize"
        notebookDisplaySizeInInches.placeholder = "14"
        notebookDisplaySizeInInches.size = 50
        notebookDisplaySizeInInches.type = "number"

        let notebookOS = document.createElement("select")
        notebookOS.id = "notebook-os"
        notebookOS.placeholder = "Windows 11"
        notebookOS.innerHTML = "Betriebssystem"
        let osOptionWindows10 = document.createElement("option")
        osOptionWindows10.value = "Windows10"
        osOptionWindows10.text = "Windows10"
        let osOptionWindows11 = document.createElement("option")
        osOptionWindows11.value = "Windows11"
        osOptionWindows11.innerText = "Windows11"
        let osOptionMac = document.createElement("option")
        osOptionMac.value = "MacOS"
        osOptionMac.innerText = "MacOS"
        let osOptionLinux = document.createElement("option")
        osOptionLinux.value = "LinuxOS"
        osOptionLinux.innerText = "LinuxOS"

        let notebookBatteryHealth = document.createElement("input")
        notebookBatteryHealth.id = "notebook-batteryhealth"
        notebookBatteryHealth.placeholder = "80%"
        notebookBatteryHealth.size = 50
        notebookBatteryHealth.max = 100
        notebookBatteryHealth.min = 1
        notebookBatteryHealth.type = "number"

        let notebookKeyboardLayout = document.createElement("input")
        notebookKeyboardLayout.id = "notebook-keyboardlayout"
        notebookKeyboardLayout.placeholder = "Deutsch"
        notebookKeyboardLayout.size = 300
        notebookKeyboardLayout.type = "text"

        let notebookSideNote = document.createElement("textarea")
        notebookSideNote.id = "notebook-displaySize"
        notebookSideNote.placeholder = "Hier mehr infos hinterlassen falls noch etwas offen ist."
        notebookSideNote.size = 300
        notebookSideNote.type = "text"

        let submitNotebookButton = document.createElement("button")
        submitNotebookButton.innerHTML = "Artikel speichern"







        // attach the elements to an element by using id in "add-article.html"
        templateForm.appendChild(notebookBrand)
        templateForm.appendChild(notebookName)
        templateForm.appendChild(notebookAmount)
        templateForm.appendChild(notebookType)
        templateForm.appendChild(notebookDescription)
        templateForm.appendChild(notebookPriceWhenBought)
        templateForm.appendChild(notebookSellingPrice)
        templateForm.appendChild(notebookModelNumber)
        templateForm.appendChild(notebookCpu)
        templateForm.appendChild(notebookRam)
        templateForm.appendChild(notebookStorageInGigs)
        templateForm.appendChild(notebookDisplaySizeInInches)
        templateForm.appendChild(notebookOS)
        templateForm.appendChild(notebookBatteryHealth)
        templateForm.appendChild(notebookKeyboardLayout)
        templateForm.appendChild(notebookSideNote)
        templateForm.appendChild(submitNotebookButton)

        formDiv.appendChild(templateForm)
        mainElement.appendChild(formDiv)
    }

    async function submitArticleAndWaitForResponse() {

    }
}