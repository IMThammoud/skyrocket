function hide() {

    if (document.getElementById("service-radio-button").checked){
        document.getElementById("article-selection-div").style.display="hidden";
    };

}

function sendShelveData(){
    let getAllRadioButtons = document.getElementsByName("is_for_service");
    let isForService;

    // checks the checked radio and stores it so i can send it with a request
    for (let i = 0; i < getAllRadioButtons.length ; i++) {
            if (getAllRadioButtons[i].checked){
                isForService = getAllRadioButtons[i].value;
            }
    }
    let myBody = { "shelve_name" : document.getElementById("shelve_name").getAttribute("shelve_name"),
                                              "is_for_service" : isForService,
                                              "category" : document.getElementById("category").value,
                                              "article-selection" : document.getElementById("article-selection").value}
    const userAction = async () => {
        const response = await fetch('http://localhost:8080/shelve/submit', {
            method: 'GET',
            body: myBody, // string or object
            headers: {
                'Content-Type': 'application/json'
            }
        });
        const myJson = response.text(); //extract Text from the http response
        document.getElementById("article-selection-div").textContent=myJson;
        console.log("hello its me sendShelveData!!!!");
        // do something with myJson
    }
}