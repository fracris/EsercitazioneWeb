function validate(event) {
    event.preventDefault();
    // Ottieni i valori degli input
    const fname = document.getElementById('fname').value.trim();
    const lname = document.getElementById('lname').value.trim();
    const email = document.getElementById('email').value.trim();
    const birthday = document.getElementById('birthday').value;
    const isHobbyist = document.getElementById('hobbyist').checked;
    const isExpert = document.getElementById('expert').checked;
    const question = document.getElementById('question').value.trim();

    // Regex per validare l'email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    // Validazioni
    if (!fname || !lname) {
        alert("Please enter both your first and last name.");
        return;
    }
    if (!email || !emailPattern.test(email)) {
        alert("Please enter a valid email address.");
        return;
    }
    if (!birthday) {
        alert("Please enter your birthday.");
        return;
    }
    if (!isHobbyist && !isExpert) {
        alert("Please select whether you are a Hobbyist or an Expert.");
        return;
    }
    if (!question) {
        alert("Please enter a question for us.");
        return;
    }

    // Se tutte le validazioni sono soddisfatte
    document.getElementById('formSubmit').submit();
}

window.addEventListener("load", function(){
    let form = document.getElementById('formSubmit');
    form.addEventListener("submit", validate);
});