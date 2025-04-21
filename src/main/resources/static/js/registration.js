async function submitRegistration() {
    let response = await fetch("/register",
        {
            method: "POST",
            "Content-Type": "application/json",
            body: JSON.stringify({
                email: document.getElementById("email").value,
                password: document.getElementById("password").value,
            })
        })
    response = await response.text();
    if (response.ok) {
        window.location.replace("/registration-success");
    }
}