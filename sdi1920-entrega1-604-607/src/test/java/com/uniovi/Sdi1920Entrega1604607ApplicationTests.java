package com.uniovi;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_AddPublicationView;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_ListUserBySearchText;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1920Entrega1604607ApplicationTests {

	// En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
	// automáticas)):
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\thali_12wmf8x\\Documents\\Clase\\SDI\\Practica-Spring\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// [Prueba1] Registro de Usuario con datos válidos
	@Test
	public void prueba01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sonia@gmail.com", "Sonia", "Garcia", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "sonia@gmail.com");

	}

	// [Prueba2] Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void prueba02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "Sonia", "Garcia", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sonia1@gmail.com", "", "Garcia", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sonia2@gmail.com", "Sonia", "", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
	}

	// [Prueba3] Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void prueba03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sonia3@gmail.com", "Sonia", "Garcia", "123456", "12345");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// [Prueba4] Registro de Usuario con datos inválidos (email existente).
	@Test
	public void prueba04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "sonia@gmail.com", "Sonia", "Garcia", "123456", "12345");
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());

	}

	// Inicio de sesión con datos válidos (administrador).
	@Test
	public void prueba05() {
		PO_Properties.getSPANISH();
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// TODO: comprobar que tiene opciones de administrador
		PO_View.checkElement(driver, "text", "Usuarios");
	}

	// Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void prueba06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "usuario@email.com", "usuario");
		
		// Comprobamos que estamos en la página de listar usuarios
		PO_View.checkElement(driver, "text", "Usuarios");
	}

	// Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos)
	@Test
	public void prueba07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "", "");
		// Comprobar que seguimos en la vista de login
		PO_View.checkElement(driver, "text", "Login");
	}

	// Inicio de sesión con datos válidos (usuario estándar, email existente, pero
	// contraseña
	// incorrecta).
	@Test
	public void prueba08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "usuario@email.com", "contraseña");
		// Comprobar que ha salido el mensaje de error
		PO_View.checkElement(driver, "text", "Usuario o contraseña incorrectos.");
	}

	// Hacer click en la opción de salir de sesión y comprobar que se redirige a la
	// página de inicio de
	// sesión (Login)
	@Test
	public void prueba09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		// Comprobar que estamos en la vista de login
		PO_View.checkElement(driver, "text", "Password");
	}

	// Comprobar que el botón cerrar sesión no está visible si el usuario no está
	// autenticado
	@Test
	public void prueba10() {
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Desconectar", PO_View.getTimeout());
	}

	// [Prueba11] Mostrar el listado de usuarios y comprobar que se muestran todos
	// los que existen en el sistema.
	@Test
	public void prueba11() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "sonia@email.com", "pass");

		// el login ya me lleva a la lista de usuarios-> comprobamos que son 3 en total
		// (hay 2 del InsertDataService y 1+ por las pruebas)
		Assert.assertEquals(3,
				SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout()).size());

		// Comprobamos que son los usuarios esperados
		PO_NavView.checkElement(driver, "text", "Thalía");
		PO_NavView.checkElement(driver, "text", "usuario@email.com"); // Estos dos son cargados por InsertDataService
		PO_View.checkElement(driver, "text", "sonia@gmail.com"); // Este es de las pruebas de registro de usuario

	}

	// [Prueba12] Hacer una búsqueda con el campo vacío y comprobar que se muestra
	// la página que corresponde con el
	// listado usuarios existentes en el sistema.

	public void prueba12() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "sonia@email.com", "pass");

		// Relleno el campo con un String vacío
		PO_ListUserBySearchText.fillSearchText(driver, "");

		// Me salen todos los usuarios del sistema
		// el login ya me lleva a la lista de usuarios-> comprobamos que son 3 en total
		// (hay 2 del InsertDataService y 1+ por las pruebas)
		Assert.assertEquals(3,
				SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout()).size());

		// Comprobamos que son los usuarios esperados
		PO_NavView.checkElement(driver, "text", "Thalía");
		PO_NavView.checkElement(driver, "text", "usuario@email.com"); // Estos dos son cargados por InsertDataService
		PO_View.checkElement(driver, "text", "sonia@gmail.com"); // Este es de las pruebas de registro de usuario

	}

	// [Prueba13] Hacer una búsqueda escribiendo en el campo un texto que no exista
	// y
	// comprobar que se muestra la página que corresponde, con la lista de usuarios
	// vacía.

	@Test
	public void prueba13() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "sonia@email.com", "pass");

		// Relleno el campo con un String que no coincida con nada
		PO_ListUserBySearchText.fillSearchText(driver, "pruebas");

		// Comprobamos que son los usuarios esperados o sea no coincide con ninguno de
		// los del sistema
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "thalia@email.com", PO_View.getTimeout());
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "usuario@email.com", PO_View.getTimeout());

	}

	// [Prueba14] Hacer una búsqueda con un texto específico y comprobar que se
	// muestra la página que corresponde,
	// con la lista de usuarios en los que el texto especificados sea parte de su
	// nombre, apellidos o de su email.

	@Test
	public void prueba14() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "sonia@email.com", "pass");

		// Relleno el campo con un String que no coincida con nada
		PO_ListUserBySearchText.fillSearchText(driver, "tha");

		// Comprobamos que son los usuarios esperados
		SeleniumUtils.EsperaCargaPagina(driver, "text", "thalia@email.com", PO_View.getTimeout());
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "sonia@email.com", PO_View.getTimeout());
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "usuario@email.com", PO_View.getTimeout());

	}

	// Mostrar el listado de invitaciones de amistad recibidas. Comprobar con un
	// listado que
	// contenga varias invitaciones recibidas
	@Test
	public void prueba17() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		// Comprobamos que hay 2
		Assert.assertEquals(2, PO_PrivateView.goToInvitations(driver).size());
		// Comprobamos que son las esperadas
		PO_NavView.checkElement(driver, "text", "usuario usuario");
		PO_NavView.checkElement(driver, "text", "admin admin");
	}

	// Sobre el listado de invitaciones recibidas. Hacer click en el botón/enlace de
	// una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones.
	@Test
	public void prueba18() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		driver.navigate().to(URL);
		PO_PrivateView.goToInvitations(driver);
		// Aceptamos la invitacion de Sonia
		PO_NavView.checkElement(driver, "id", "accept/usuario").get(0).click();

		// Comprobamos que ya no está
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "usuario usuario", PO_View.getTimeout());
	}
	
	
	
	//[Prueba19] Mostrar el listado de amigos de un usuario. Comprobar que el listado contiene los amigos que deben ser.
	@Test
	public void prueba19() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "sonia@email.com", "pass");
		
		
		
		//Navegamos hasta la opción de listar amigos de un usuario en sesión
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friends/list')]");
		elementos.get(0).click();
		
		//Comprobamos el número de amigos del usuario
		Assert.assertEquals(2,
				SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout()).size());

		// Comprobamos que son los usuarios esperados
		PO_NavView.checkElement(driver, "text", "thalia@email.com");
		PO_NavView.checkElement(driver, "text", "usuario@email.com"); // Estos dos son cargados por InsertDataService
		
		
	}

	// Ir al formulario crear publicaciones, rellenarla con datos válidos y pulsar
	// el botón Submit.
	// Comprobar que la publicación sale en el listado de publicaciones de dicho
	// usuario.
	@Test
	public void prueba24() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		driver.navigate().to(URL);

		PO_HomeView.checkElement(driver, "id", "publications-menu").get(0).click();
		PO_HomeView.checkElement(driver, "@href", "/publication/add").get(0).click();

		PO_AddPublicationView.fillForm(driver, "Prueba24", "Esto es una prueba");

		// TODO: comprobar que estamos en la vista de listar mis publicaciones
		// TODO: comprobar que aparece la nueva publicacion
	}

	// Ir al formulario de crear publicaciones, rellenarla con datos inválidos
	// (campo título vacío) y
	// pulsar el botón Submit. Comprobar que se muestra el mensaje de campo
	// obligatorio.
	@Test
	public void prueba25() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		PO_HomeView.checkElement(driver, "id", "publications-menu").get(0).click();
		PO_HomeView.checkElement(driver, "@href", "/publication/add").get(0).click();

		PO_AddPublicationView.fillForm(driver, " ", " ");

		PO_NavView.checkKey(driver, "Error.publication.title", PO_Properties.getSPANISH());
	}

	// Desde el formulario de crear publicaciones, crear una publicación con datos
	// válidos y una
	// foto adjunta. Comprobar que en el listado de publicaciones aparecer la foto
	// adjunta junto al resto de
	// datos de la publicación
	@Test
	public void prueba29() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		PO_HomeView.checkElement(driver, "id", "publications-menu").get(0).click();
		PO_HomeView.checkElement(driver, "@href", "/publication/add").get(0).click();

		PO_AddPublicationView.fillForm(driver, "Prueba", "Esto es una prueba automática.", "C:\\logo.png");

		// TODO: comprobar que aparece en el listado de publicaciones
	}

	// Crear una publicación con datos válidos y sin una foto adjunta. Comprobar que
	// la
	// publicación se a creado con éxito, ya que la foto no es obligatoria.
	@Test
	public void prueba30() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "thalia@email.com", "pass");

		driver.navigate().to(URL);

		PO_HomeView.checkElement(driver, "id", "publications-menu").get(0).click();
		PO_HomeView.checkElement(driver, "@href", "/publication/add").get(0).click();

		PO_AddPublicationView.fillForm(driver, "Prueba24", "Esto es una prueba");

		// TODO: comprobar que estamos en la vista de listar mis publicaciones
		// TODO: comprobar que aparece la nueva publicacion
	}
}
