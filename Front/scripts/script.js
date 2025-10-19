
const tabRegister = document.getElementById('tab-register');
const tabLogin = document.getElementById('tab-login');
const formRegister = document.getElementById('form-register');
const formLogin = document.getElementById('form-login');
const goLogin = document.getElementById('go-login');
const goRegister = document.getElementById('go-register');

tabRegister.addEventListener('click', () => {
  tabRegister.classList.add('active');
  tabLogin.classList.remove('active');
  formRegister.classList.add('active');
  formLogin.classList.remove('active');
});

tabLogin.addEventListener('click', () => {
  tabLogin.classList.add('active');
  tabRegister.classList.remove('active');
  formLogin.classList.add('active');
  formRegister.classList.remove('active');
});

goLogin.addEventListener('click', () => {
  tabLogin.click();
});

goRegister.addEventListener('click', () => {
  tabRegister.click();
});

formLogin.addEventListener("submit", async (e) => {
  e.preventDefault(); 
  debugger

  const email = document.getElementById("email").value;
  const contrasenia = document.getElementById("contrasenia").value;

  const loginData = { email, contrasenia };

  console.log("Datos del formulario:", loginData);


  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginData)
    });

    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }

    const result = await response.text();
    console.log("Respuesta del servidor:", result);
  } catch (error) {
    console.error("Error al enviar datos:", error);
  }
});