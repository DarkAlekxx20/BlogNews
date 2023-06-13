function login() {
  let nombreUsuario = document.getElementById("txtNombreL").value;
  let contrasenia = document.getElementById("txtContraseniaL").value;
  let datos = JSON.stringify({nombreUsuario: nombreUsuario,contrasenia: contrasenia});
  params = new URLSearchParams({ datos: datos });
  console.log(datos);
  fetch("api/usuario/login",{
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"},
    body: params
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      if (data.error) {
        alert("Error al iniciar sesion");
      } else {
        alert("Bienvenido " + nombreUsuario.toString());
        localStorage.setItem("currentUser", JSON.stringify(data));
        window.location.href ="http://localhost:8080/BlogNews/Modules/dashboard.html";
      }
    });
}

const $btnSignIn = document.querySelector(".sign-in-btn"),
  $btnSignUp = document.querySelector(".sign-up-btn"),
  $signUp = document.querySelector(".sign-up"),
  $signIn = document.querySelector(".sign-in");

document.addEventListener("click", (e) => {
  if (e.target === $btnSignIn || e.target === $btnSignUp) {
    $signIn.classList.toggle("active");
    $signUp.classList.toggle("active");
  }
});
