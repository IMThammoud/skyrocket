// This renders the shelves as options on the selection when adding a new article
// It fetches a json array uses its key value pairs for the options in selection tab of "add-article.html"
let shelveIdOfSelectedShelve
let notebookObject = {
    "pk_id":"",
    "name":"",
    "amount":"",
    "type":"",
    "description":"",
    "price_when_bought":"",
    "selling_price":"",
    "fk_shelve_id":"",
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

    // When clicking on add article to shelve on UI
    let request = await fetch("http://localhost:8080/add/article/check-shelve-type",
        {
            headers : {"content-type" : "application/json;charset=UTF8"},
            method : "POST",
            body : JSON.stringify({ "shelve": document.getElementById("shelve").value}),
        })
    let response = await request.text()
    switch (response) {
        // Add some other cases in future and mirror the cases with the endpoint on the server, storing global ShelveID to use it in request function
        case "notebook":
            shelveIdOfSelectedShelve = document.getElementById("shelve").value
            console.log("ShelveID of selected shelve:" + shelveIdOfSelectedShelve)
            document.getElementById("replaceableWithJS").remove()
            build_notebook_form()

        case "smartphone":
            shelveIdOfSelectedShelve = document.getElementById("shelve").value
            console.log("ShelveID of selected shelve:" + shelveIdOfSelectedShelve)
            document.getElementById("replaceableWithJS").remove()
            build_smartphone_form()

        default:
            console.log("No suitable type returned by the server for template generation.")
            break;
    }

    function build_notebook_form(){
        // Building the form
        let formDiv = document.createElement("div")
        formDiv.id = "Js-built-form"

        let mainElement = document.getElementById("main-element")
        let buttonSpace = document.getElementById("Button-space-in-form")

        let templateForm = document.createElement("form")
        templateForm.id = "template-form"

        // Create label and link its for property to the input form
        let label_notebookbrand = document.createElement("label")
        label_notebookbrand.innerText = "Marke"
        label_notebookbrand.htmlFor = "notebook-brand"
        let notebookBrand = document.createElement("input")
        notebookBrand.id = "notebook-brand"
        notebookBrand.placeholder = "Lenovo"
        notebookBrand.type = "text"

        let label_notebookname = document.createElement("label")
        label_notebookname.innerText = "Name"
        label_notebookname.htmlFor = "notebook-name"
        let notebookName = document.createElement("input")
        notebookName.id = "notebook-name"
        notebookName.placeholder = "Thinkpad T14 Gen5"
        notebookName.type = "text"

        let label_notebookAmount = document.createElement("label")
        label_notebookAmount.innerText = "Anzahl"
        label_notebookAmount.htmlFor = "notebook-amount"
        let notebookAmount = document.createElement("input")
        notebookAmount.id = "notebook-amount"
        notebookAmount.placeholder = "1..10.."
        notebookAmount.size = 20
        notebookAmount.type = "number"

        // Need to add selection with types for the typefield
        let label_notebookType = document.createElement("label")
        label_notebookType.innerText = "Typ"
        label_notebookType.htmlFor = "notebook-typ"
        let notebookType = document.createElement("select")
        notebookType.id = "notebook-type"
        notebookType.placeholder = "Standard, Convertable"
        notebookType.type = "select-one"
        let optionStandard = document.createElement("option")
        optionStandard.innerText = "Standard"
        optionStandard.value = "standard"
        let optionConvertible = document.createElement("option")
        optionConvertible.innerText = "Convertible"
        optionConvertible.value = "convertible"
        let optionTwoInOneTablet = document.createElement("option")
        optionTwoInOneTablet.innerText = "2-in-1-tablet"
        optionTwoInOneTablet.value = "2-in-1-tablet"
        notebookType.appendChild(optionStandard)
        notebookType.appendChild(optionConvertible)
        notebookType.appendChild(optionTwoInOneTablet)

        let label_notebookDescription = document.createElement("label")
        label_notebookDescription.innerText = "Kurze Beschreibung"
        label_notebookDescription.htmlFor = "notebook-description"
        let notebookDescription = document.createElement("textarea")
        notebookDescription.id = "notebook-description"
        notebookDescription.placeholder = "Light and portable Workstation that works efficient and has all-day batterylife."
        notebookDescription.type = "text"

        let label_notebookPriceWhenBought = document.createElement("label")
        label_notebookPriceWhenBought.innerText = "Einkaufspreis"
        label_notebookPriceWhenBought.htmlFor = "notebook-price-when-bought"
        let notebookPriceWhenBought = document.createElement("input")
        notebookPriceWhenBought.id = "notebook-price-when-bought"
        notebookPriceWhenBought.placeholder = "429,99€"
        notebookPriceWhenBought.min = "0"
        notebookPriceWhenBought.size = 30
        notebookPriceWhenBought.type = "number"

        let label_notebookSellingPrice = document.createElement("label")
        label_notebookSellingPrice.innerText = "Listenverkaufspreis"
        label_notebookSellingPrice.htmlFor = "notebook-selling-price"
        let notebookSellingPrice = document.createElement("input")
        notebookSellingPrice.id = "notebook-selling-price"
        notebookSellingPrice.placeholder = "429,99€"
        notebookSellingPrice.min = "0"
        notebookSellingPrice.size = 30
        notebookSellingPrice.type = "number"

        let label_notebookModelNumber = document.createElement("label")
        label_notebookModelNumber.innerText = "Modellnummer"
        label_notebookModelNumber.htmlFor = "notebook-modelnumber"
        let notebookModelNumber = document.createElement("input")
        notebookModelNumber.id = "notebook-modelnumber"
        notebookModelNumber.placeholder = "20SUB-QSYYC"
        notebookModelNumber.size = 50
        notebookModelNumber.type = "text"

        let label_notebookCpu = document.createElement("label")
        label_notebookCpu.innerText = "CPU / Prozessor"
        label_notebookCpu.htmlFor = "notebook-cpu"
        let notebookCpu = document.createElement("input")
        notebookCpu.id = "notebook-cpu"
        notebookCpu.placeholder = "Intel Core i5 or i5 1235u"
        notebookCpu.size = 30
        notebookCpu.type = "text"

        let label_notebookRam = document.createElement("label")
        label_notebookRam.innerText = "RAM / Arbeitsspeicher in GBs"
        label_notebookRam.htmlFor = "notebook-ram"
        let notebookRam = document.createElement("input")
        notebookRam.id = "notebook-ram"
        notebookRam.placeholder = "16"
        notebookRam.min = 0
        notebookRam.max = 1000
        notebookRam.size = 30
        notebookRam.type = "number"

        let label_notebookStorageInGigs = document.createElement("label")
        label_notebookStorageInGigs.innerText = "Festplatte / SSD / HDD Speicher"
        label_notebookStorageInGigs.htmlFor = "notebook-storage"
        let notebookStorageInGigs = document.createElement("input")
        notebookStorageInGigs.id = "notebook-storage"
        notebookStorageInGigs.placeholder = "1000GB"
        notebookStorageInGigs.min = 0
        notebookStorageInGigs.size = 30
        notebookStorageInGigs.type = "number"
        //notebookStorageInGigs.pattern = "[0-9]{3}-[0-9]{3}-[0-9]{4}"


        let label_notebookDisplaySizeInInches = document.createElement("label")
        label_notebookDisplaySizeInInches.innerText = "Display in Zoll"
        label_notebookDisplaySizeInInches.htmlFor = "notebook-displaySize"
        let notebookDisplaySizeInInches = document.createElement("input")
        notebookDisplaySizeInInches.id = "notebook-displaySize"
        notebookDisplaySizeInInches.min = "0"
        notebookDisplaySizeInInches.placeholder = "14"
        notebookDisplaySizeInInches.size = 30
        notebookDisplaySizeInInches.type = "number"

        let label_notebookOS = document.createElement("label")
        label_notebookOS.innerText = "Betriebssystem"
        label_notebookOS.htmlFor = "notebook-os"

        let notebookOS = document.createElement("select")
        notebookOS.id = "notebook-os"
        notebookOS.placeholder = "Windows 11"
        notebookOS.innerHTML = "Betriebssystem"
        let osOptionWindows10 = document.createElement("option")
        osOptionWindows10.value = "Windows10"
        osOptionWindows10.innerText = "Windows10"
        let osOptionWindows11 = document.createElement("option")
        osOptionWindows11.value = "Windows11"
        osOptionWindows11.innerText = "Windows11"
        let osOptionMac = document.createElement("option")
        osOptionMac.value = "MacOS"
        osOptionMac.innerText = "MacOS"
        let osOptionLinux = document.createElement("option")
        osOptionLinux.value = "LinuxOS"
        osOptionLinux.innerText = "LinuxOS"
        notebookOS.appendChild(osOptionWindows10)
        notebookOS.appendChild(osOptionWindows11)
        notebookOS.appendChild(osOptionMac)
        notebookOS.appendChild(osOptionLinux)

        let label_notebookBatteryHealth = document.createElement("label")
        label_notebookBatteryHealth.innerText = "Batteryhealth in %"
        label_notebookBatteryHealth.htmlFor = "notebook-batteryhealth"
        let notebookBatteryHealth = document.createElement("input")
        notebookBatteryHealth.id = "notebook-batteryhealth"
        notebookBatteryHealth.placeholder = "80%"
        notebookBatteryHealth.size = 50
        notebookBatteryHealth.max = 100
        notebookBatteryHealth.min = 1
        notebookBatteryHealth.type = "number"

        let label_notebookKeyboardLayout = document.createElement("label")
        label_notebookKeyboardLayout.innerText = "Keyboard Layout"
        label_notebookKeyboardLayout.htmlFor = "notebook-keyboardlayout"
        let notebookKeyboardLayout = document.createElement("input")
        notebookKeyboardLayout.id = "notebook-keyboardlayout"
        notebookKeyboardLayout.placeholder = "Deutsch"
        notebookKeyboardLayout.size = 300
        notebookKeyboardLayout.type = "text"

        let label_notebookSideNote = document.createElement("label")
        label_notebookSideNote.innerText = "Bemerkung, Notiz"
        label_notebookSideNote.id = "notebook-sidenote"
        let notebookSideNote = document.createElement("textarea")
        notebookSideNote.id = "notebook-sidenote"
        notebookSideNote.placeholder = "Hier mehr infos hinterlassen falls noch etwas offen ist."
        notebookSideNote.size = 300
        notebookSideNote.type = "text"

        let submitNotebookButton = document.createElement("button")
        submitNotebookButton.id = "submitNotebookButton"
        submitNotebookButton.style = "margin-top: 5%"
        submitNotebookButton.innerHTML = "Artikel speichern"

        // submitNotebookButton.onclick
        //Adding eventlistener is necessary to lock all values into notebookObject and submit it
        submitNotebookButton.addEventListener("click", function() {
            notebookObject.pk_id = ""
            notebookObject.fk_shelve_id = shelveIdOfSelectedShelve
            notebookObject.name = notebookName.value
            notebookObject.amount = notebookAmount.value
            notebookObject.type = notebookType.value
            notebookObject.description = notebookDescription.value
            notebookObject.price_when_bought = notebookPriceWhenBought.value
            notebookObject.selling_price = notebookSellingPrice.value
            notebookObject.brand = notebookBrand.value
            notebookObject.model_nr = notebookModelNumber.value
            notebookObject.cpu = notebookCpu.value
            notebookObject.ram = notebookRam.value
            notebookObject.storage_in_gb = notebookStorageInGigs.value
            notebookObject.display_size_inches = notebookDisplaySizeInInches.value
            notebookObject.operating_system = notebookOS.value
            notebookObject.battery_capacity_health = notebookBatteryHealth.value
            notebookObject.keyboard_layout = notebookKeyboardLayout.value
            notebookObject.side_note = notebookSideNote.value;
            submitArticleAndWaitForResponse(notebookObject);}
        );

        //////////////////////////////////////////////////////////////////////
        // attach the elements to an element by using id in "add-article.html"
        templateForm.appendChild(label_notebookbrand)
        templateForm.appendChild(notebookBrand)

        templateForm.appendChild(label_notebookname)
        templateForm.appendChild(notebookName)

        templateForm.appendChild(label_notebookAmount)
        templateForm.appendChild(notebookAmount)

        templateForm.appendChild(label_notebookType)
        templateForm.appendChild(notebookType)

        templateForm.appendChild(label_notebookDescription)
        templateForm.appendChild(notebookDescription)

        templateForm.appendChild(label_notebookPriceWhenBought)
        templateForm.appendChild(notebookPriceWhenBought)

        templateForm.appendChild(label_notebookSellingPrice)
        templateForm.appendChild(notebookSellingPrice)

        templateForm.appendChild(label_notebookModelNumber)
        templateForm.appendChild(notebookModelNumber)

        templateForm.appendChild(label_notebookCpu)
        templateForm.appendChild(notebookCpu)

        templateForm.appendChild(label_notebookRam)
        templateForm.appendChild(notebookRam)

        templateForm.appendChild(label_notebookStorageInGigs)
        templateForm.appendChild(notebookStorageInGigs)

        templateForm.appendChild(label_notebookDisplaySizeInInches)
        templateForm.appendChild(notebookDisplaySizeInInches)

        templateForm.appendChild(label_notebookOS)
        templateForm.appendChild(notebookOS)

        templateForm.appendChild(label_notebookBatteryHealth)
        templateForm.appendChild(notebookBatteryHealth)

        templateForm.appendChild(label_notebookKeyboardLayout)
        templateForm.appendChild(notebookKeyboardLayout)

        templateForm.appendChild(label_notebookSideNote)
        templateForm.appendChild(notebookSideNote)

        formDiv.appendChild(templateForm)
        formDiv.appendChild(submitNotebookButton)

        mainElement.appendChild(formDiv)
    }

    function build_smartphone_form() {
        return "build smartphone form here."
    }

    async function submitArticleAndWaitForResponse(notebookObject) {
        console.log(notebookObject)
        let request =  await fetch("http://localhost:8080/add/article/receiveArticle", {
            headers: {"Content-Type": "application/json"},
            method: "POST",
            body: JSON.stringify(notebookObject)
        })
        let response = await request.text()
        if (response === "success") {
            alert("Article added to shelve")
        } else {
            alert("Server does not like your form. ")
        }
    }
}