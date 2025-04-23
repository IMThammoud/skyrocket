// sends request with userinput to endpoint and
// checks if user exists

 async function login(){
     const myHeaders = new Headers();
     myHeaders.append("Content-Type", "application/json");
    let request = await fetch("/login",{
        headers: myHeaders,
        method:"POST",
        body: JSON.stringify({
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        }),

    })

     if (await request.text() === "true" || "alreadyHasAccount") {
         window.location.replace("/shelve/shelves")
     } else {
         alert("Anmeldung fehlgeschlagen. Bitte laden sie die Seite neu und versuchen sie es noch einmal.")
     }

}