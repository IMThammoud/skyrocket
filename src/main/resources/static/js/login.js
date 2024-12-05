// sends request with userinput to endpoint and
// checks if user exists

 async function login(){
     const myHeaders = new Headers();
     myHeaders.append("Content-Type", "application/json");
    let request = await fetch("http://localhost:8080/login",{
        headers: myHeaders,
        method:"POST",
        body: JSON.stringify({
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        }),

    })

     if (await request.text() === "true") {
         window.location.replace("http://localhost:8080/shelve/shelves")
     } else {
         alert("Anmeldung fehlgeschlagen. Bitte laden sie die Seite neu und versuchen sie es noch einmal.")
     }

}