
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

  const email = document.getElementById("email").value;
  const contrasenia = document.getElementById("contrasenia").value;

  const loginData = { email, contrasenia };

  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginData)
    });

    if (!response.ok) {
      throw new Error(`Error HTTP: ${response}`);
    }else{
      const result = await response.json();
      localStorage.setItem("token", result.token);
      localStorage.setItem("nombre", result.nombre);
      localStorage.setItem("apellido", result.apellido);
      localStorage.setItem("email", result.email);
      localStorage.setItem("rol", result.rol);

      window.location.href = "/menu_principal.html"
    }
  } catch (error) {
    console.error("Error al enviar datos:", error);
  }
});

formRegister.addEventListener("submit", async (e) => {
  e.preventDefault();

  const nombre = document.getElementById("reg-nombre").value;
  const apellido = document.getElementById("reg-apellido").value;
  const contrasenia = document.getElementById("reg-password").value;
  const email = document.getElementById("reg-email").value;
  const rol = 1;

  const registerData = { nombre, email, apellido, contrasenia, rol };

  try {
    const response = await fetch("http://localhost:8080/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(registerData)
    });

    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }

    const result = await response.json();

  } catch (error) {
    console.error("Error al enviar datos:", error);
  }

})