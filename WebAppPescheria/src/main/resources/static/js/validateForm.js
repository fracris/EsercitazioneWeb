function validate(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(email)) {
        alert("Please enter a valid email address.");
        return;
    }
    document.getElementById('formSubmit').submit();
    document.getElementById('formSubmit').reset();
}

window.addEventListener("load", function(){
    let form = document.getElementById('formSubmit');
    form.addEventListener("submit", validate);
});