let noticias = [];
let comentarios = [];
let respuestas = [];

function inizializar() {
  getAllNoticias();
}

function fecha(){
  let fechaActual = new Date();
  let dia = fechaActual.getDate();
  let mes = fechaActual.getMonth() + 1;
  let anio = fechaActual.getFullYear();
  let fechaActualCadena = `${dia < 10 ? "0" + dia : dia}/${
    mes < 10 ? "0" + mes : mes
  }/${anio}`;
  return fechaActualCadena;
}

function agregarNota() {
  let currentUser = localStorage.getItem("currentUser");
  let usuario = JSON.parse(currentUser);
  if (usuario.rol == "Interno"){
    document.getElementById("noticias").classList.add("d-none");
    document.getElementById("formularioNoticia").classList.remove("d-none");
    document.getElementById("txtPersonal").value = usuario.nombreUsuario;
  }else{
    alert("No tienes los permisos para agregar una nota");
  }
}

function regresar() {
  document.getElementById("formularioNoticia").classList.add("d-none");
  document.getElementById("noticias").classList.remove("d-none");
}

function enviarNoticia() {
  let currentUser = localStorage.getItem("currentUser");
  let usuario = JSON.parse(currentUser);
  let datos = null;
  let params = null;
  let noticia = new Object();
  noticia.personal = new Object();
  noticia.titulo = document.getElementById("txtTitulo").value;
  noticia.contenido = document.getElementById("txtContenido").value;
  noticia.fechaPublicacion = fecha();
  noticia.personal.idPersonal = usuario.idUsuario;
  datos = {
    datos: JSON.stringify(noticia),
  };
  params = new URLSearchParams(datos);
  fetch("../api/noticia/agregar", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
    },
    body: params,
  })
    .then((response) => {
      return response.json();
    })
    .then((data) =>{
      if (data.error){
        alert("Error al guardar la noticia");
      }else{
        alert("La noticia se genero con exito");
        inizializar();
        regresar();
      }
    });
}

function getAllNoticias() {
  fetch("../api/noticia/getAll")
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      if (data.error) {
        alert("Error al solicitar los datos de noticia a la base de datos");
      } else {
        loadTableNoticias(data);
      }
    });
}

function loadTableNoticias(data) {
  fetch("../api/comentario/getAll")
    .then((response) => {
      return response.json();
    })
    .then((dataC) => {
      if (dataC.error) {
        alert("Error al solicitar los datos de comentario a la base de datos");
      } else {
        comentarios = dataC;
        fetch("../api/respuesta/getAll")
          .then((response) => {
            return response.json();
          })
          .then((dataR) => {
            if (dataR.error) {
              alert(
                "Error al solicitar los datos de respuestas a la base de datos"
              );
            } else {
              respuestas = dataR;
              let cuerpo = `<div class="card col-lg-4 justify-content-center align-content-center d-flex">
                                                                    <button class="btn btn-primary m-5 p-5" onclick="agregarNota();">Agregar una nueva noticia</button>
                                                                </div>`;

              noticias = data;
              console.log(noticias);
              console.log(comentarios);
              noticias.forEach(function (noticia) {
                let comentariosNoticia = comentarios.filter(function (
                  comentario
                ) {
                  return comentario.noticia.idNoticia === noticia.idNoticia;
                });

                let registroComentarios = "";
                comentariosNoticia.forEach(function (comentario) {
                  registroComentarios +=
                    `<h4>` +
                    comentario.texto +
                    `</h4>
                                                        <input type="text" id="txtR` +
                    comentario.idComentario +
                    `" class="mb-2" placeholder="Responder a este comentario"/>
                                                        <button class="btn btn-primary mb-2" onclick="enviarRespuesta(` +
                    comentario.idComentario +
                    `);">Responder</button>
                                                        <hr>`;

                  let respuestasFiltradas = respuestas.filter(function (
                    respuesta
                  ) {
                    return (
                      respuesta.comentario.idComentario ===
                      comentario.idComentario
                    );
                  });

                  respuestasFiltradas.forEach(function (respuesta) {
                    registroComentarios +=
                      `<h5>` +
                      respuesta.texto +
                      `</h5><hr><hr>`;
                  });
                });

                let registro =
                  `<div class="card col-lg-4 align-content-center d-flex">
                                          <h6 class="text-end">` +
                  noticia.fechaPublicacion.substring(0, 10) +
                  `</h6>
                                          <h1 class="text-center">` +
                  noticia.titulo +
                  `</h1>
                                          <hr>
                                          <h2 class="text-center">` +
                  noticia.contenido +
                  `</h2>
                                          <hr>
                                          <h3 class="text-end">Escrito por ` +
                  noticia.personal.nombre +
                  `</h3>
                                          <hr>
                                          <h4>Comentarios</h4>
                                          ` +
                  registroComentarios +
                  `
                                          <input type="text" id="txtC` +
                  noticia.idNoticia +
                  `" class="mb-2" placeholder="Escribe tu comentario"/>
                                          <button class="btn btn-success mb-2" onclick="enviarComentario(` +
                  noticia.idNoticia +
                  `);">Enviar</button>
                                          </div>`;
                cuerpo += registro;
              });
              document.getElementById("llenarNoticias").innerHTML = cuerpo;
            }
          });
      }
    });
}

function enviarComentario(idNoticia) {
  let currentUser = localStorage.getItem("currentUser");
  let usuario = JSON.parse(currentUser);
  let comentarioText = "txtC" + idNoticia;

  let datos = null;
  let params = null;

  let comentario = new Object();
  comentario.noticia = new Object();
  comentario.usuario = new Object();

  comentario.texto = document.getElementById(comentarioText).value;
  comentario.fechayHora = fecha();
  comentario.noticia.idNoticia = idNoticia;
  comentario.usuario.idUsuario = usuario.idUsuario;

  datos = {
    datos: JSON.stringify(comentario),
  };

  params = new URLSearchParams(datos);

  fetch("../api/comentario/agregar",{
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
    },
    body: params,
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      if (data.error) {
        alert("Error al enviar el comentario");
      } else {
        alert("Comentario generado con exito");
        inizializar();
      }
    });
}

function enviarRespuesta(idComentario){
  let currentUser = localStorage.getItem("currentUser");
  let usuario = JSON.parse(currentUser);
  let respuestaText = "txtR" + idComentario;

  let datos = null;
  let params = null;

  let respuesta = new Object();
  respuesta.comentario = new Object();
  respuesta.usuario = new Object();

  respuesta.texto = document.getElementById(respuestaText).value;
  respuesta.fechayHora = fecha();
  respuesta.comentario.idComentario = idComentario;
  respuesta.usuario.idUsuario = usuario.idUsuario;

  datos = {
    datos: JSON.stringify(respuesta),
  };

  params = new URLSearchParams(datos);
  fetch("../api/respuesta/agregar", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
    },
    body: params,
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      if (data.error) {
        alert("Error al enviar la respuesta");
      } else {
        alert("La respuesta se genero con exito");
        inizializar();
      }
    });
}